package laboflieven;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lieven on 14/06/2015.
 */
public class TestcaseInOutParameters
{
    public TestcaseInOutParameters() {
        input = new HashMap<>();
        expectedOutput = new HashMap<>();
    }

    public static TestcaseInOutParameters createParameter(double[] doubles, double result, int expectedOutputRegister)
    {
        return createParameter(doubles, result, expectedOutputRegister, "R");
    }

    public static TestcaseInOutParameters createParameter(double[] doubles, double result, int expectedOutputRegister, String registerPrefix)
    {
        Map<String, Double> startParameters  = getMap(doubles, registerPrefix);
        Map<String, Double> endParameters = new HashMap<>(1);
        endParameters.put(registerPrefix+expectedOutputRegister, result);
        TestcaseInOutParameters parameters = new TestcaseInOutParameters();
        parameters.input = startParameters;
        parameters.expectedOutput = endParameters;
        return parameters;
    }

    public static double[] fillDoubleArray(double[] original, int newSize)
    {
        return Arrays.copyOf(original, newSize);
    }
    public static Map<String, Double> getMap(double... doubles)
    {
        Map<String, Double> results = new HashMap<>();
        for (int l = 1; l <= doubles.length; l++)
        {
            results.put("R"+l, doubles[l - 1]);
        }
        return results;
    }

    private static Map<String, Double> getMap(double[] doubles, String prefix)
    {
        Map<String, Double> results = new HashMap<>();
        for (int l = 0; l < doubles.length; l++)
        {
            results.put(prefix+(l + 1), doubles[l]);
        }
        return results;
    }
    public Map<String, Double> input;
    public Map<String, Double> expectedOutput;

    public String toString()
    {
        return input.toString() + " => " + expectedOutput.toString();
    }

}
