package laboflieven.challenges;

import laboflieven.TestcaseInOutParameters;
import laboflieven.instructions.accinstructions.InstructionFactory;
import laboflieven.common.Configuration;
import laboflieven.examiners.ProgramFitnessExaminer;
import laboflieven.examiners.ProgramFitnessExaminerInterface;
import laboflieven.loggers.TimingAccFitnessLogger;
import laboflieven.programiterators.AccPriorityProgramIterator;
import laboflieven.programiterators.ProgramIterator;
import laboflieven.runners.AccStatementRunner;

import java.io.IOException;
import java.util.List;

public class SumOfFractionsFinder implements ProgramTemplate
{
    public static double sum(double nr) {
        double sum = 0;
        for (int i = 1; i <= nr; i++) {
            sum += 1/(double)i;
        }
        return sum;
    }


    public static void main(String[] args) throws IOException {
        int curMaxRegisters = 1;
        List<TestcaseInOutParameters> collection = new TestCases().getAllTestCases(new SumOfFractionsFinder(), TestCases.getExampleInput1D(20,1),curMaxRegisters);


        ProgramFitnessExaminerInterface evaluator = new ProgramFitnessExaminer(collection, new AccStatementRunner());
        evaluator.addListener(new TimingAccFitnessLogger(1000));
        ProgramIterator iter = new AccPriorityProgramIterator();

        long start = System.currentTimeMillis();
        var config = new Configuration()
                .setFitnessExaminer(evaluator)
                .setInstructionFactory(new InstructionFactory())
                .setMaxNrInstructions(15)
                .setNumberOfRegisters(1);

        iter.iterate(config);
        //evaluator.writeAndClose();
        System.out.println(System.currentTimeMillis() - start + "ms");

        /*iter = new BruteForceProgramIterator(evaluator, new AlwaysRecursionHeuristic());
        start = System.currentTimeMillis();
        iter.iterate(curMaxRegisters, 4);
        //evaluator.writeAndClose();
        */
        System.out.println( "19000ms");

    }

    @Override
    public double run(double[] args) {
        return sum(args[0]);
    }
}
