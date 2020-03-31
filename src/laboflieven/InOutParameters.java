package laboflieven;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lieven on 14/06/2015.
 */
public class InOutParameters
{
    public InOutParameters() {
        input = new HashMap<>();
        expectedOutput = new HashMap<>();
    }

    public static InOutParameters createParameter(double[] doubles, double result, int expectedOutputRegister)
    {
        return createParameter(doubles, result, expectedOutputRegister, "r");
    }

    public static InOutParameters createParameter(double[] doubles, double result, int expectedOutputRegister, String registerPrefix)
    {
        Map<String, Double> startParameters  = getMap(doubles, registerPrefix);
        Map<String, Double> endParameters = new HashMap<>(1);
        endParameters.put(registerPrefix+expectedOutputRegister, result);
        InOutParameters parameters = new InOutParameters();
        parameters.input = startParameters;
        parameters.expectedOutput = endParameters;
        return parameters;
    }

    public static double[] fillDoubleArray(double[] original, int newSize)
    {
        return Arrays.copyOf(original, newSize);
    }
    private static Map<String, Double> getMap(double[] doubles)
    {
        Map<String, Double> results = new HashMap<>();
        for (int l = 0; l < doubles.length; l++)
        {
            results.put("r"+l, doubles[l]);
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

}
