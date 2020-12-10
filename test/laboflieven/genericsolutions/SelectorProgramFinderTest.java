package laboflieven.genericsolutions;

import laboflieven.challenges.TestcaseSource;
import laboflieven.challenges.XorFinder;
import laboflieven.common.Configuration;
import laboflieven.examiners.ProgramFitnessExaminer;
import laboflieven.runners.AccStatementRunner;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SelectorProgramFinderTest {

    @Test
    void findSolutions() {
        TestcaseSource source = new XorFinder();
        var  selector = new SelectorProgramFinder();
        var evaluator = new ProgramFitnessExaminer(source.getTestCases(), new AccStatementRunner());
        var configuration = new Configuration();
        configuration.setFitnessExaminer(evaluator);
        var prog = selector.findSolutions(configuration);
        assertNotNull(prog);
    }
    @Test
    void getTestCases()
    {
        TestcaseSource source = new XorFinder();
        var selector = new SelectorProgramFinder();
        var testcases = selector.adjustTestcases(source.getTestCases());
        assertEquals(0, testcases.get(0).expectedOutput.get("R1"));
        assertEquals(1, testcases.get(1).expectedOutput.get("R1"));
        assertEquals(2, testcases.get(2).expectedOutput.get("R1"));

    }
}