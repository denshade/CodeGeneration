package laboflieven.challenges;

import laboflieven.examiners.ProgramFitnessExaminer;
import laboflieven.examiners.ProgramFitnessExaminerInterface;
import laboflieven.programiterators.AccRandomGeneticProgramIterator;
import laboflieven.InOutParameters;
import laboflieven.accinstructions.AccInstructionSet;
import laboflieven.loggers.SysOutAccFitnessLogger;
import laboflieven.runners.AccStatementRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class P2 implements ProgramTemplate
{
    /*

     */
    public static void main(String[] args) throws IOException {

        int curMaxRegisters = 4;
        List<double[]> points = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            points.add(new double[] { i, Math.sqrt(5),0,0});
        }
        List<InOutParameters> collection = TestCases.getTestCases(new P2(), points.toArray(new double[0][0]),curMaxRegisters);
        ProgramFitnessExaminerInterface evaluator = new ProgramFitnessExaminer(collection, new AccStatementRunner());
        evaluator.addListener(new SysOutAccFitnessLogger(10000));
        AccRandomGeneticProgramIterator iter = new AccRandomGeneticProgramIterator(evaluator,  AccInstructionSet.values(), 10000,1.2,0.4);
        long start = System.currentTimeMillis();
        System.out.println(iter.iterate(curMaxRegisters, 15));
        //evaluator.writeAndClose();
        System.out.println(System.currentTimeMillis() - start + "ms");

        //mainT(15,3);
    }

    @Override
    public double run(double[] args) {
        return F((int)args[0]);
    }

    public long F(int n)
    {
        if (n  == 0) return 0;
        if (n  == 1) return 1;
        return F(n - 1 ) + F(n - 2);
    }


}
