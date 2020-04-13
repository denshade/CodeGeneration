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
        return getExampleInput2D(100,10);
    }
    public static double[][] getExampleInput2D(int max, int jump)
    {
        List<double[]> points = new ArrayList<>();
        for (int x = 0; x < max; x+= jump){
            for (int y = 0; y < max; y += jump)
            {
                points.add(new double[] {(double) x, (double) y});
            }
        }
        return points.toArray(new double[0][0]);
    }

    public static double[][] getBooleanInput2D()
    {
        List<double[]> points = new ArrayList<>();
        points.add(new double[] { 0,0});
        points.add(new double[] { 0,1});
        points.add(new double[] { 1,0});
        points.add(new double[] { 1,1});
        return points.toArray(new double[0][0]);
    }

    public static double[][] getExampleInput4D(int max, int jump)
    {
        List<double[]> points = new ArrayList<>();
        for (int x = 0; x < max; x+= jump){
            for (int y = 0; y < max; y += jump)
                for (int z = 0; z < max; z+= jump)
                    for (int a = 0; a < max; a += jump)
            {
                points.add(new double[] {(double) x, (double) y, (double)z, (double)a});
            }
        }
        return points.toArray(new double[0][0]);
    }


    public static double[][] getExampleInput1D(int max, int jump)
    {
        List<double[]> points = new ArrayList<>();
        for (int x = 0; x < max; x+= jump){
            {
                points.add(new double[] {(double) x});
            }
        }
        return points.toArray(new double[0][0]);
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
        endParameters.put("R1", result);
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
            results.put("R"+(l+1), doubles[l]);
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
