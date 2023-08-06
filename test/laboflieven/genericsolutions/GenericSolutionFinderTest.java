package laboflieven.genericsolutions;

import laboflieven.TestcaseInOutParameters;
import laboflieven.challenges.*;
import laboflieven.registers.NumberNamingScheme;
import laboflieven.runners.GenericSolutionAccRunner;
import laboflieven.registers.Register;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GenericSolutionFinderTest {

    @Disabled
    @Test
    void findSolution() {
        TestcaseSource source = new XorFinder();
        GenericSolutionFinder finder = new GenericSolutionFinder();
        assertNotNull(finder.findSolution(source.getTestCases()));
        var solution = finder.findSolution(source.getTestCases());
        assertTrue(solution.programSelector.size() > 0);
        assertEquals(source.getTestCases().size(), solution.programs.size());
        System.out.println(solution);
        GenericSolutionAccRunner runner = new GenericSolutionAccRunner();
        for (int i = 0; i < source.getTestCases().size(); i++) {
            assertEquals(source.getTestCases().get(i).expectedOutput.get("R1"), runner.execute(solution, source.getTestCases().get(i).input, new NumberNamingScheme().createRegisters(2)).get("R1"));
        }
    }

    @Test
    void findCosAltSolution() {
        List<TestcaseInOutParameters> testcases = new TestCases().getAllTestCases(new CosAltFinder(), TestCases.getExampleInput1D(10, 1),1);
        GenericSolutionFinder finder = new GenericSolutionFinder();
        assertNotNull(finder.findSolution(testcases));
        var solution = finder.findSolution(testcases);
        assertTrue(solution.programSelector.size() > 0);
        assertEquals(testcases.size(), solution.programs.size());
        System.out.println(solution);
        GenericSolutionAccRunner runner = new GenericSolutionAccRunner();
        for (int i = 0; i < testcases.size(); i++) {
            System.out.println(i);
            assertEquals(testcases.get(i).expectedOutput.get("R1"), runner.execute(solution, testcases.get(i).input, new NumberNamingScheme().createRegisters(1)).get("R1"));
        }
    }
}