package laboflieven.challenges;

import laboflieven.InOutParameters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class TestCases
{
    public static double[][] getExampleInput2D()
    {
        double[][] doubles = {new double[]{ 10, 1}, new double[]{ 1, 10}, new double[]{ 1, 1},
                new double[]{ 0, 0}, new double[]{ 1, 100},
                new double[]{ 1000, 50}, new double[]{ 1000, 1}, new double[]{ 50, 1},
                new double[]{ 10000, 50}, new double[]{ 10000, -1}, new double[]{ -10000, -100}

        };
        return doubles;
    }

    public static double[][] getExampleInput1D()
    {
        double[][] doubles = {new double[]{ 10}, new double[]{ 1}, new double[]{ 1},
                new double[]{ 0}, new double[]{ 1},
                new double[]{ 1000},  new double[]{ 50},
                new double[]{ 10000}, new double[]{ -10000}

        };
        return doubles;
    }


    public static InOutParameters createParameter(double[] doubles, double result)
    {
        Map<String, Double> startParameters  = getMap(doubles);
        Map<String, Double> endParameters = new HashMap<>(1);
        endParameters.put("r0", result);
        InOutParameters parameters = new InOutParameters();
        parameters.input = startParameters;
        parameters.expectedOutput = endParameters;
        return parameters;
    }
    public static Map<String, Double> getMap(double[] doubles)
    {
        Map<String, Double> results = new HashMap<>();
        for (int l = 0; l < doubles.length; l++)
        {
            results.put("r"+l, doubles[l]);
        }
        return results;
    }

    public static double[] fillDoubleArray(double[] original, int newSize)
    {
        double[] result = new double[newSize];
        System.arraycopy(original, 0, result, 0, original.length);
        return result;
    }

    public static List<InOutParameters> getTestCases(ProgramTemplate template, double[][] doubles, int curMaxRegisters)
    {
        List<InOutParameters> collection = new ArrayList<>();
        for (double[] doubleRow : doubles)
        {
            double solution = template.run(doubleRow);
            if (!Double.isNaN(solution) && !Double.isInfinite(solution))
                collection.add(TestCases.createParameter(TestCases.fillDoubleArray(doubleRow, curMaxRegisters), solution));
        }
        return collection;
    }
}