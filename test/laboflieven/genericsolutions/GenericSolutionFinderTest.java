package laboflieven.genericsolutions;

import laboflieven.challenges.TestcaseSource;
import laboflieven.challenges.XorFinder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GenericSolutionFinderTest {

    @Test
    void findSolution() {
        TestcaseSource source = new XorFinder();
        GenericSolutionFinder finder = new GenericSolutionFinder();
        assertNotNull(finder.findSolution(source.getTestCases()));
        var solution = finder.findSolution(source.getTestCases());
        assertTrue(solution.programSelector.size() > 0);
        assertTrue(solution.programs.size() == source.getTestCases().size());
        System.out.println(solution);
    }
}