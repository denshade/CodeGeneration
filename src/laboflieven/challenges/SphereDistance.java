package laboflieven.challenges;

import laboflieven.InOutParameters;
import laboflieven.ProgramFitnessExaminer;
import laboflieven.ReverseProgramIterator;
import laboflieven.statements.InstructionEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SphereDistance
{
    public static double distance(double lat1, double lat2, double lon1, double lon2) {
        final int R = 6371;
// Radius of the earth
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters double height = el1 - el2; distance = Math.pow(distance, 2) + Math.pow(height, 2);
        return Math.sqrt(distance);
    }

    private static double calcQuad(double[] args)
    {
        double a = args[0];
        double b = args[1];
        double c = args[2];
        return (-b + (Math.sqrt(b*b - 4*a*c))) / 2*a;
    }

    private static double[] fillDoubleArray(double[] original, int newSize)
    {
        double[] result = new double[newSize];
        for (int i = 0; i < original.length; i++)
        {
            result[i] = original[i];
        }
        return result;
    }

    private static InOutParameters createParameter(double[] doubles, double result)
    {
        Map<String, Double> startParameters  = getMap(doubles);
        Map<String, Double> endParameters = new HashMap<>(1);
        endParameters.put("r3", result);
        InOutParameters parameters = new InOutParameters();
        parameters.input = startParameters;
        parameters.expectedOutput = endParameters;
        return parameters;
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

    public static void main(String[] args)
    {
        int curMaxRegisters = 4;
        List<InOutParameters> collection = new ArrayList<>();
        collection.add(createParameter(fillDoubleArray(new double [] {2.0,-8.0,-24.0,0}, curMaxRegisters), distance(2.0,-8.0,-24.0,0)));
        collection.add(createParameter(fillDoubleArray(new double [] {1.0, 2.0, 1.0,0}, curMaxRegisters), distance(1.0, 2.0, 1.0,0)));// -1
        collection.add(createParameter(fillDoubleArray(new double [] {1.0, 2.0, -3.0,0}, curMaxRegisters), distance(1.0, 2.0, -3.0,0)));
        collection.add(createParameter(fillDoubleArray(new double [] {1.0, -1, -56,0}, curMaxRegisters), distance(1.0, -1, -56,0)));
        collection.add(createParameter(fillDoubleArray(new double [] {1.0, 2, -15,0}, curMaxRegisters), distance(1.0, 2.0, -15,0)));
        collection.add(createParameter(fillDoubleArray(new double [] {1.0, -100, 2500,0}, curMaxRegisters), distance(1.0, -100, 2500,0)));
        collection.add(createParameter(fillDoubleArray(new double [] {1.0, -200, 10000,0}, curMaxRegisters), distance(1.0, -200, 10000,0)));
        collection.add(createParameter(fillDoubleArray(new double [] {1.0, -400, 40000,0}, curMaxRegisters), distance(1.0, -400, 40000,0)));
        collection.add(createParameter(fillDoubleArray(new double [] {1.0, 500, 0,0}, curMaxRegisters), distance(1.0, 500, 0,0)));
        collection.add(createParameter(fillDoubleArray(new double [] {1.0, 15000, 0,0}, curMaxRegisters), distance(1.0, 15000, 0,0)));
        collection.add(createParameter(fillDoubleArray(new double [] {-1.0, 15000, 0,0}, curMaxRegisters), distance(-1.0, 15000, 0,0)));
        collection.add(createParameter(fillDoubleArray(new double [] {2.0, 1000, 0,0}, curMaxRegisters), distance(2.0, 1000, 0,0)));


        ProgramFitnessExaminer evaluator = new ProgramFitnessExaminer(collection);

        ReverseProgramIterator iter = new ReverseProgramIterator(evaluator, new InstructionEnum[]{InstructionEnum.Add, InstructionEnum.Sub, InstructionEnum.Mul, InstructionEnum.Div, InstructionEnum.Sqrt, InstructionEnum.Move});
        iter.iterate(curMaxRegisters, 14);

    }

}
