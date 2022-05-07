package laboflieven.challenges;

import laboflieven.TestcaseInOutParameters;
import laboflieven.accinstructions.AccInstructionOpcodeEnum;
import laboflieven.common.Configuration;
import laboflieven.examiners.ProgramFitnessExaminer;
import laboflieven.examiners.ProgramFitnessExaminerInterface;
import laboflieven.loggers.RandomSysOutAccFitnessLogger;
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
        List<TestcaseInOutParameters> collection = TestCases.getTestCases(new P2Bis(), points.toArray(new double[0][0]),curMaxRegisters);
        ProgramFitnessExaminerInterface evaluator = new ProgramFitnessExaminer(collection, new AccStatementRunner());
        evaluator.addListener(new RandomSysOutAccFitnessLogger(10000));
        var conf = new Configuration();
        conf.setMaxNrInstructions(4);
        conf.setFitnessExaminer(evaluator);
        conf.setNumberOfRegisters(curMaxRegisters);
        conf.setAccOperations(AccInstructionOpcodeEnum.values());
        conf.setHeuristic(new CombinedHeuristic(List.of(
                new ResultsNoDataAfterFiveHeuristic(
                        new AccStatementRunner(),
                        evaluator,
                        Register.createRegisters(curMaxRegisters)
                )
                ,
                new AccHeuristic()
        )));
        GeneralBruteForceProgramIterator iter = new GeneralBruteForceProgramIterator();
        long start = System.currentTimeMillis();
        System.out.println(iter.iterate(conf));
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
