package laboflieven.programiterators;

import laboflieven.TestcaseInOutParameters;
import laboflieven.challenges.AdderFinder;
import laboflieven.challenges.TestCases;
import laboflieven.common.Configuration;
import laboflieven.examiners.ProgramFitnessExaminer;
import laboflieven.examiners.ProgramFitnessExaminerInterface;
import laboflieven.loggers.TimingAccFitnessLogger;
import laboflieven.recursionheuristics.HashedResultsHeuristic;
import laboflieven.runners.AccStatementRunner;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GeneralBruteForceProgramIteratorTest {

    @Test
    void iterate() {

        Configuration conf = new Configuration();
        conf.setNumberOfRegisters(2);
        conf.setMaxNrInstructions(4);
        List<TestcaseInOutParameters> collection = TestCases.getTestCases(new AdderFinder(), TestCases.getExampleInput2D(1000,100, 10), 2);
        ProgramFitnessExaminerInterface evaluator = new ProgramFitnessExaminer(collection, new AccStatementRunner());
        evaluator.addListener(new TimingAccFitnessLogger(10000));
        conf.setFitnessExaminer(evaluator);
        GeneralBruteForceProgramIterator iter = new GeneralBruteForceProgramIterator();
        assertNotNull(iter.iterate(conf));
    }

    @Test
    void iterateWithHeuristics() {

        Configuration conf = new Configuration();
        conf.setNumberOfRegisters(2);
        conf.setMaxNrInstructions(4);
        List<TestcaseInOutParameters> collection = TestCases.getTestCases(new AdderFinder(), TestCases.getExampleInput2D(1000,100, 10), 2);
        conf.setHeuristic(new HashedResultsHeuristic(collection, new AccStatementRunner()));
        ProgramFitnessExaminerInterface evaluator = new ProgramFitnessExaminer(collection, new AccStatementRunner());
        evaluator.addListener(new TimingAccFitnessLogger(10000));
        conf.setFitnessExaminer(evaluator);
        GeneralBruteForceProgramIterator iter = new GeneralBruteForceProgramIterator();
        assertNotNull(iter.iterate(conf));

    }

}