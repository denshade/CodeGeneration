package laboflieven.challenges;

import laboflieven.InOutParameters;
import laboflieven.accinstructions.AccInstructionOpcodeEnum;
import laboflieven.examiners.ProgramFitnessExaminer;
import laboflieven.examiners.ProgramFitnessExaminerInterface;
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

public class P2Bis implements ProgramTemplate
{
    /*
[[ left = R1,  right = R2, left = left + right, R1 = left]]
     */
    public static void main(String[] args) throws IOException {

        int curMaxRegisters = 2;
        List<double[]> points = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            points.add(new double[] { i, i + 1});
        }
        List<InOutParameters> collection = TestCases.getTestCases(new P2Bis(), points.toArray(new double[0][0]),curMaxRegisters);
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
        System.out.println(iter.iterate(curMaxRegisters, 4));
        //evaluator.writeAndClose();
        System.out.println(System.currentTimeMillis() - start + "ms");

        //mainT(15,3);
    }

    @Override
    public double run(double[] args) {
        return F((int)args[0], (int)args[1]);
    }

    public long F(int n1, int n2)
    {
        return n1 + n2;
    }


}
