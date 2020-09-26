package laboflieven.challenges;

import laboflieven.InOutParameters;
import laboflieven.common.CommandLineConfigLoader;
import laboflieven.common.Configuration;
import laboflieven.examiners.ProgramFitnessExaminer;
import laboflieven.examiners.ProgramFitnessExaminerInterface;
import laboflieven.loggers.RandomSysOutAccFitnessLogger;
import laboflieven.loggers.TimingAccFitnessLogger;
import laboflieven.programiterators.RandomProgramIterator;
import laboflieven.runners.AccStatementRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MAX_NR_OF_INSTRUCTIONS=10 INSTRUCTION_FACTORY=Acc
 */
public class EllipseFinder implements ProgramTemplate
{
    public static double distance(double a, double b) {
        double h = ((a-b)*(a-b)) / ((a+b) * (a+b));
        return Math.PI * (a + b) * ( 1+ ( 3*h) / (10 + Math.sqrt(4 - 3 * h)));
    }


    public static void main(String[] args) {
        CommandLineConfigLoader loader = new CommandLineConfigLoader();
        Configuration config = loader.loadFromCommandLine(args);
        System.out.println(distance(100,300));
        int curMaxRegisters = config.getNumberOfRegisters(2);
        List<InOutParameters> collection = TestCases.getTestCases(new EllipseFinder(), TestCases.getExampleInput2D(10000,100), curMaxRegisters);

        ProgramFitnessExaminerInterface evaluator = new ProgramFitnessExaminer(collection, new AccStatementRunner());
        evaluator.addListener(new TimingAccFitnessLogger(10000));

        config.setFitnessExaminer(evaluator);
        /*Configuration config = Configuration.getInstance();
        config.setMaxNrInstructions(nrInstructions);
        config.setFitnessExaminer(evaluator);
        config.setInstructionFactory(new InstructionFactory());*/

        RandomProgramIterator iter = new RandomProgramIterator();
        long start = System.currentTimeMillis();
        iter.iterate(config);
        System.out.println(System.currentTimeMillis() - start + "ms");
    }

    @Override
    public double run(double[] args) {
        return distance(args[0], args[1]);
    }
}
