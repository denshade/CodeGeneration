package laboflieven.functional.examiners;

import laboflieven.TestcaseInOutParameters;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class AnyRegisterProgramTestcaseFitnessTest {

    @Test
    public void testPerfectAnswer()
    {
        var fitness = new AnyRegisterProgramTestcaseFitness();
        HashMap<String, Double> results = new HashMap<>();
        results.put("R1", 1.0);
        var cases = new TestcaseInOutParameters();
        cases.expectedOutput.put("R1",0.0);
        fitness.calculateError(results, "R1", new TestcaseInOutParameters(), "R1", 1000000 );
    }

}