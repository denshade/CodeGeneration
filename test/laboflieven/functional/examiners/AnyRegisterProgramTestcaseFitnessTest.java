package laboflieven.functional.examiners;

import laboflieven.TestcaseInOutParameters;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class AnyRegisterProgramTestcaseFitnessTest {

    @Test
    public void testPerfectAnswer()
    {
        HashMap<String, Double> results = new HashMap<>();
        results.put("R1", 1.0);
        var cases = new TestcaseInOutParameters();
        cases.expectedOutput.put("R1", 1.0);
        assertEquals(0.0, AnyRegisterProgramTestcaseFitness.calculateError(results, "R1", cases, "R1", 1000000 ));
    }
    @Test
    public void testWrongAnswer()
    {
        HashMap<String, Double> results = new HashMap<>();
        results.put("R1", 2.0);
        var cases = new TestcaseInOutParameters();
        cases.expectedOutput.put("R1", 1.0);
        assertEquals(1.0,AnyRegisterProgramTestcaseFitness.calculateError(results, "R1", cases, "R1", 1000000 ));
    }

    @Test
    public void testWrongRegisterAnswer()
    {
        HashMap<String, Double> results = new HashMap<>();
        results.put("AL", 2.0);
        results.put("R1", 200.0);
        var cases = new TestcaseInOutParameters();
        cases.expectedOutput.put("R1", 2.0);
        assertEquals(10.0,AnyRegisterProgramTestcaseFitness.calculateError(results, "R1", cases, "R1", 1000000 ));
    }

}