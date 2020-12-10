package laboflieven.challenges;

import laboflieven.*;
import laboflieven.examiners.ProgramFitnessExaminer;
import laboflieven.examiners.ProgramFitnessExaminerInterface;
import laboflieven.programiterators.BruteForceProgramIterator;
import laboflieven.runners.RegularStatementRunner;
import laboflieven.statements.RegularInstructionOpcodeEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Lieven on 8/07/2015.
 */
public class AdderFinder implements ProgramTemplate{

    /**
     * //No solutions for 2 -> 5.
     *
     * @param args
     */
    public static void main(String[] args)
    {
        List<TestcaseInOutParameters> collection = new ArrayList<>();
        collection.add(createParameter(fillDoubleArray(new double [] {0.0, 0.0, 0.0}, 3), new double[] {0.0,0.0}));
        collection.add(createParameter(fillDoubleArray(new double [] {0.0, 0.0, 1.0}, 3), new double[] {0.0,1.0}));
        collection.add(createParameter(fillDoubleArray(new double [] {0.0, 1.0, 0.0}, 3), new double[] {0.0,1.0}));
        collection.add(createParameter(fillDoubleArray(new double [] {0.0, 1.0, 1.0}, 3), new double[] {1.0,0.0}));
        collection.add(createParameter(fillDoubleArray(new double [] {1.0, 0.0, 0.0}, 3), new double[] {0.0,1.0}));
        collection.add(createParameter(fillDoubleArray(new double [] {1.0, 0.0, 1.0}, 3), new double[] {1.0,0.0}));
        collection.add(createParameter(fillDoubleArray(new double [] {1.0, 1.0, 0.0}, 3), new double[] {1.0,0.0}));
        collection.add(createParameter(fillDoubleArray(new double [] {1.0, 1.0, 1.0}, 3), new double[] {1.0,1.0}));

        ProgramFitnessExaminerInterface evaluator = new ProgramFitnessExaminer(collection, new RegularStatementRunner());
        BruteForceProgramIterator iterator = new BruteForceProgramIterator(evaluator, new RegularInstructionOpcodeEnum[] {RegularInstructionOpcodeEnum.Nand});
        iterator.iterate(3,10);
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

    private static TestcaseInOutParameters createParameter(double[] doubles, double[] results)
    {
        Map<String, Double> startParameters  = getMap(doubles);
        Map<String, Double> endParameters = getMap(results);
        TestcaseInOutParameters parameters = new TestcaseInOutParameters();
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

    @Override
    public double run(double[] args) {
        return args[0] + args[1];
    }
}
