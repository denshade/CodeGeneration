package laboflieven.challenges;

import laboflieven.TestcaseInOutParameters;
import laboflieven.accinstructions.AccInstructionOpcodeEnumBuilder;
import laboflieven.common.CommandLineConfigLoader;
import laboflieven.common.Configuration;
import laboflieven.examiners.ProgramFitnessExaminer;
import laboflieven.examiners.ProgramFitnessExaminerInterface;
import laboflieven.loggers.TimingAccFitnessLogger;
import laboflieven.programiterators.ProgramIterator;
import laboflieven.programiterators.RandomProgramIterator;
import laboflieven.runners.SumSeriesAccStatementRunner;

import java.util.List;

/**
 * MAX_NR_OF_INSTRUCTIONS=10 INSTRUCTION_FACTORY=Acc DATA_PROVIDER=laboflieven.challenges.EllipseFinder PROGRAM_ITERATOR=random
 * MAX_NR_OF_INSTRUCTIONS=100 INSTRUCTION_FACTORY=Acc DATA_PROVIDER=laboflieven.challenges.EllipseFinder PROGRAM_ITERATOR=genetic MAX_POPULATION=1000 MAX_OVERFLOW=1.2 POPULAR_PARENT_PART=0.5
 * MAX_NR_OF_INSTRUCTIONS=20 INSTRUCTION_FACTORY=Acc DATA_PROVIDER=laboflieven.challenges.EllipseFinder PROGRAM_ITERATOR=brute ACC_OPERATIONS=nobranch
 * MAX_NR_OF_INSTRUCTIONS=20 INSTRUCTION_FACTORY=Acc DATA_PROVIDER=laboflieven.challenges.EllipseFinder PROGRAM_ITERATOR=brute ACC_OPERATIONS=nobranch
 * MAX_NR_OF_INSTRUCTIONS=20 INSTRUCTION_FACTORY=Acc DATA_PROVIDER=laboflieven.challenges.EllipseFinder PROGRAM_ITERATOR=priority ACC_OPERATIONS=nobranch
 * MAX_NR_OF_INSTRUCTIONS=50 INSTRUCTION_FACTORY=Acc DATA_PROVIDER=laboflieven.challenges.EllipseFinder PROGRAM_ITERATOR=priority ACC_OPERATIONS=nobranch RND_ADDED=false
 */
public class SeriesGeneralFinder
{

    public static void main(String[] args) {
        loadConfigAndRun(args);
    }

    public static void loadConfigAndRun(String[] args)
    {
        CommandLineConfigLoader loader = new CommandLineConfigLoader();
        Configuration config = loader.loadFromCommandLine(args);
        int curMaxRegisters = config.getNumberOfRegisters(2);
        config.setAccOperations(AccInstructionOpcodeEnumBuilder.make().noGeo().build());
        List<TestcaseInOutParameters> collection = TestCases.getTestCases(config.getDataProvider(), TestCases.getExampleInput1D(1,0.01), curMaxRegisters);

        ProgramFitnessExaminerInterface evaluator = new ProgramFitnessExaminer(collection, new SumSeriesAccStatementRunner());
        evaluator.addListener(new TimingAccFitnessLogger(10000));
        //config.setHeuristic(new HashedResultsHeuristic(collection, new AccStatementRunner()));
        config.setFitnessExaminer(evaluator);
        System.out.println("Running with the following config: " + config);
        ProgramIterator iter = config.getProgramIterator(new RandomProgramIterator());
        long start = System.currentTimeMillis();
        iter.iterate(config);
        System.out.println(System.currentTimeMillis() - start + "ms");
    }
}
