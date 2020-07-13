package laboflieven.challenges;

import laboflieven.*;
import laboflieven.examiners.ProgramFitnessExaminer;
import laboflieven.examiners.ProgramFitnessExaminerInterface;
import laboflieven.programiterators.RandomGeneticProgramIterator;
import laboflieven.runners.RegularStatementRunner;
import laboflieven.statements.RegularInstructionOpcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AtanFinder
{
    public static double distance(double x, double y) {
        return Math.atan2(x,y);
    }

    private static double[] fillDoubleArray(double[] original, int newSize)
    {
        double[] result = new double[newSize];
        System.arraycopy(original, 0, result, 0, original.length);
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
        for (double x = Math.PI/2 * -1; x < Math.PI/2; x += 0.1)
        {
            for (double y = Math.PI/2 * -1; y < Math.PI/2; y += 0.1)
            {
                collection.add(createParameter(fillDoubleArray(new double [] {x,y}, curMaxRegisters), distance(x,y)));
            }

        }

        /*        collection.add(createParameter(fillDoubleArray(new double [] {1.0, 2.0, 1.0,0}, curMaxRegisters), distance(1.0, 2.0, 1.0,0)));// -1
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
*/

        ProgramFitnessExaminerInterface evaluator = new ProgramFitnessExaminer(collection, new RegularStatementRunner());

        //ReverseProgramIterator iter = new ReverseProgramIterator(evaluator, new InstructionEnum[]{InstructionEnum.Add, InstructionEnum.Sub, InstructionEnum.Mul, InstructionEnum.Div, InstructionEnum.Sqrt, InstructionEnum.Move, InstructionEnum.Sin, InstructionEnum.Cos});
        RandomGeneticProgramIterator iter = new RandomGeneticProgramIterator(evaluator, RegularInstructionOpcode.values(),
                10000, 1.3, 0.4);

        iter.iterate(curMaxRegisters, 15);

    }

}
