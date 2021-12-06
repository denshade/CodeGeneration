package laboflieven.challenges;

import laboflieven.TestcaseInOutParameters;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

public class TestCases
{
    public static List<TestcaseInOutParameters> loadFromCsvFile(File sourceFile) throws IOException {

        System.out.println("Searching for " + sourceFile);

        if (!sourceFile.exists()) {
            System.out.println("Source files not found "+ sourceFile);
            System.exit(1);
        }
        String lines = String.join("\n", Files.readAllLines(sourceFile.toPath()));
        return  TestCases.loadFromCsvString(lines, 1);
    }

    public static List<TestcaseInOutParameters> loadFromCsvString(String csvData, int columnToPredict) {
        List<TestcaseInOutParameters> collection = new ArrayList<>();
        String[] lines = csvData.split("\n");
        boolean isFirstLine = true;
        for (String line : lines)
        {
            if (isFirstLine) {
                isFirstLine = false;
                continue;
            }
            String[] parts = line.split(",");
            double[] points = new double[parts.length-1];
            int counter = 0;
            for (int k = 0; k < parts.length; k++)
            {
                if (k == columnToPredict - 1) continue;
                points[counter++] = Double.parseDouble(parts[k]);
            }
            collection.add(TestCases.createParameter(points, Double.parseDouble(parts[columnToPredict - 1])));
        }
        return collection;
    }

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

    public static double[][] getExampleInput2D(int max, int jump, int min)
    {
        List<double[]> points = new ArrayList<>();
        for (int x = min; x < max; x+= jump){
            for (int y = min; y < max; y += jump)
            {
                points.add(new double[] {(double) x, (double) y});
            }
        }
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

    public static double[][] getExampleInput1D(double max, double jump)
    {
        List<double[]> points = new ArrayList<>();
        for (double x = 0; x < max; x+= jump){
            {
                points.add(new double[] { x});
            }
        }
        return points.toArray(new double[0][0]);
    }

    public static double[][] getExampleInput1D()
    {
        return new double[][]{new double[]{ 10}, new double[]{ 1}, new double[]{ 1},
                new double[]{ 0}, new double[]{ 1},
                new double[]{ 1000},  new double[]{ 50},
                new double[]{ 10000}, new double[]{ -10000}

        };
    }


    public static TestcaseInOutParameters createParameter(double[] doubles, double result)
    {
        Map<String, Double> startParameters  = getMap(doubles);
        Map<String, Double> endParameters = new HashMap<>(1);
        endParameters.put("R1", result);
        TestcaseInOutParameters parameters = new TestcaseInOutParameters();
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

    public static List<TestcaseInOutParameters> getTestCases(ProgramTemplate template, double[][] doubles, int curMaxRegisters)
    {
        if (template == null) {
            throw new IllegalArgumentException("Template must not be null");
        }

        List<TestcaseInOutParameters> collection = new ArrayList<>();
        for (double[] doubleRow : doubles)
        {
            double solution = template.run(doubleRow);
            if (!Double.isNaN(solution) && !Double.isInfinite(solution))
                collection.add(TestCases.createParameter(TestCases.fillDoubleArray(doubleRow, curMaxRegisters), solution));
        }
        return collection;
    }
}
