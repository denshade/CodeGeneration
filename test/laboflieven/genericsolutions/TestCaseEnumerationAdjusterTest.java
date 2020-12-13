package laboflieven.genericsolutions;

import laboflieven.challenges.TestcaseSource;
import laboflieven.challenges.XorFinder;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TestCaseEnumerationAdjusterTest {

    @Test
    void getTestCases()
    {
        TestcaseSource source = new XorFinder();
        var selector = new TestCaseEnumerationAdjuster();
        var testcases = selector.adjustTestcases(source.getTestCases());
        assertEquals(0, testcases.get(0).expectedOutput.get("R1"));
        assertEquals(1, testcases.get(1).expectedOutput.get("R1"));
        assertEquals(2, testcases.get(2).expectedOutput.get("R1"));
    }

    @Test
    void adjustTestcases() {
        TestcaseSource source = new XorFinder();
        var selector = new TestCaseEnumerationAdjuster();
        var testcases = selector.adjustTestcases(source.getTestCases(), List.of(0,1,0,0));
        assertEquals(0, testcases.get(0).expectedOutput.get("R1"));
        assertEquals(1, testcases.get(1).expectedOutput.get("R1"));
        assertEquals(0, testcases.get(2).expectedOutput.get("R1"));
        assertEquals(0, testcases.get(3).expectedOutput.get("R1"));

    }
}