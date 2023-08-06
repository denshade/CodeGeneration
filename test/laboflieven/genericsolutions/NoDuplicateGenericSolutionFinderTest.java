package laboflieven.genericsolutions;

import laboflieven.TestcaseInOutParameters;
import laboflieven.challenges.CosAltFinder;
import laboflieven.challenges.TestCases;
import laboflieven.runners.GenericSolutionAccRunner;
import laboflieven.registers.Register;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NoDuplicateGenericSolutionFinderTest {
    @Test
    public void testNoDuplicates()
    {
        List<TestcaseInOutParameters> testcases = TestCases.getTestCases(new CosAltFinder(), TestCases.getExampleInput1D(10, 1),1);
        var finder = new NoDuplicateGenericSolutionFinder();
        var solution = finder.findSolution(testcases);
        assertNotNull(solution);
        assertTrue(solution.programSelector.size() > 0);
        assertEquals(3, solution.programs.size());
        System.out.println(solution);
        GenericSolutionAccRunner runner = new GenericSolutionAccRunner();
        for (int i = 0; i < testcases.size(); i++) {
            System.out.println(i);
            assertEquals(testcases.get(i).expectedOutput.get("R1"), runner.execute(solution, testcases.get(i).input, Register.createRegisters(1)).get("R1"));
        }
    }

}