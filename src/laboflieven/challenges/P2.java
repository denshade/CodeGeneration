package laboflieven.challenges;

import laboflieven.examiners.ProgramFitnessExaminer;
import laboflieven.examiners.ProgramFitnessExaminerInterface;
import laboflieven.InOutParameters;
import laboflieven.accinstructions.AccInstructionOpcodeEnum;
import laboflieven.loggers.SysOutAccFitnessLogger;
import laboflieven.programiterators.GeneralBruteForceProgramIterator;
import laboflieven.recursionheuristics.AccHeuristic;
import laboflieven.recursionheuristics.CombinedHeuristic;
import laboflieven.recursionheuristics.ResultsNoDataAfterFiveHeuristic;
import laboflieven.runners.AccStatementRunner;
import laboflieven.statements.Register;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class P2 implements ProgramTemplate
{
    /*
[ Jump if left >= right goto this + 2, left = nand(left, right),  left = R1,  Jump if left <= right goto this + 2, left = log(left), R1 = left, R1 = left, left = log(left),  left = R1,  right = R1,  right = R1,  right = R1, left = left * right, left = left ^ right, R1 = left]
     */
    public static void main(String[] args) throws IOException {

        int curMaxRegisters = 2;
        List<double[]> points = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            points.add(new double[] { i, Math.sqrt(5)});
        }
        List<InOutParameters> collection = TestCases.getTestCases(new P2(), points.toArray(new double[0][0]),curMaxRegisters);
        ProgramFitnessExaminerInterface evaluator = new ProgramFitnessExaminer(collection, new AccStatementRunner());
        evaluator.addListener(new SysOutAccFitnessLogger(10000));
        GeneralBruteForceProgramIterator iter = new GeneralBruteForceProgramIterator(evaluator,
                AccInstructionOpcodeEnum.values(),
                new CombinedHeuristic(List.of(
                new ResultsNoDataAfterFiveHeuristic(
                        new AccStatementRunner(),
                        evaluator,
                        Register.createRegisters(curMaxRegisters, "R")
                )
                ,
                        new AccHeuristic()
                        )));
        long start = System.currentTimeMillis();
        System.out.println(iter.iterate(curMaxRegisters, 6));
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
