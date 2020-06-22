package laboflieven.challenges;

import laboflieven.*;
import laboflieven.programiterators.BruteForceProgramIterator;
import laboflieven.programiterators.RandomGeneticProgramIterator;
import laboflieven.statements.InstructionEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Lieven on 8/07/2015.
 */
public class DistanceFinder {

    public static void main(String[] args)
    {
        int curMaxRegisters = 4;
        List<InOutParameters> collection = new ArrayList<>();
        collection.add(createParameter(fillDoubleArray(new double [] {0.0,0.0,0.0,0.0}, curMaxRegisters), 0.0));
        collection.add(createParameter(fillDoubleArray(new double [] {1.0,1.0,1.0,1.0}, curMaxRegisters), 0.0));
        collection.add(createParameter(fillDoubleArray(new double [] {1.0,1.0,1.0,1.0}, curMaxRegisters), 0.0));
        collection.add(createParameter(fillDoubleArray(new double [] {2.0,2.0,2.0,2.0}, curMaxRegisters), Math.sqrt((2-2)*(2-2) + (2-2)*(2-2))));
        collection.add(createParameter(fillDoubleArray(new double [] {1.0,2.0,2.0,2.0}, curMaxRegisters), Math.sqrt((1-2)*(1-2) + (2-2)*(2-2))));
        collection.add(createParameter(fillDoubleArray(new double [] {1.0,2.0,0.0,2.0}, curMaxRegisters), Math.sqrt((1-2)*(1-2) + (0-2)*(0-2))));
        collection.add(createParameter(fillDoubleArray(new double [] {10.0,3.0,5.0,1.0}, curMaxRegisters), Math.sqrt((10-3)*(10-3) + (5-1)*(5-1))));
        collection.add(createParameter(fillDoubleArray(new double [] {100.0,50.0,50.0,100.0}, curMaxRegisters), Math.sqrt((100-50)*(100-50) + (50-100)*(50-100))));


        ProgramFitnessExaminerInterface evaluator = new ProgramFitnessExaminer(collection);

        BruteForceProgramIterator iterator = new BruteForceProgramIterator(evaluator);
       iterator.iterate(4, 6);
    }
    /**
     * //No solutions for 2 -> 5.
     *
     * @param args
     */
    public static void main2(String[] args)
    {
        double maxPopulationOverflow = 1.1;
        //double popularParents = .8;

        double winnerOfTheWorldWeight = 45000;
        String winner = "";
        for (int maxSizePopulation = 50000; maxSizePopulation < 100000; maxSizePopulation += 10000)
        for (double curpopularParents = 0.8; curpopularParents < 0.9; curpopularParents+= 0.2)
        for (int curMaxRegisters = 4; curMaxRegisters < 5; curMaxRegisters++)
        {

            List<InOutParameters> collection = new ArrayList<>();
            collection.add(createParameter(fillDoubleArray(new double [] {0.0,0.0,0.0,0.0}, curMaxRegisters), 0.0));
            collection.add(createParameter(fillDoubleArray(new double [] {1.0,1.0,1.0,1.0}, curMaxRegisters), 0.0));
            collection.add(createParameter(fillDoubleArray(new double [] {1.0,1.0,1.0,1.0}, curMaxRegisters), 0.0));
            collection.add(createParameter(fillDoubleArray(new double [] {2.0,2.0,2.0,2.0}, curMaxRegisters), Math.sqrt((2-2)*(2-2) + (2-2)*(2-2))));
            collection.add(createParameter(fillDoubleArray(new double [] {1.0,2.0,2.0,2.0}, curMaxRegisters), Math.sqrt((1-2)*(1-2) + (2-2)*(2-2))));
            collection.add(createParameter(fillDoubleArray(new double [] {1.0,2.0,0.0,2.0}, curMaxRegisters), Math.sqrt((1-2)*(1-2) + (0-2)*(0-2))));
            collection.add(createParameter(fillDoubleArray(new double [] {10.0,3.0,5.0,1.0}, curMaxRegisters), Math.sqrt((10-3)*(10-3) + (5-1)*(5-1))));
            collection.add(createParameter(fillDoubleArray(new double [] {100.0,50.0,50.0,100.0}, curMaxRegisters), Math.sqrt((100-50)*(100-50) + (50-100)*(50-100))));


            ProgramFitnessExaminerInterface evaluator = new ProgramFitnessExaminer(collection);

            for (int curMaxInstructions = 5; curMaxInstructions < 7; curMaxInstructions++) {

                RandomGeneticProgramIterator iterator = new RandomGeneticProgramIterator(evaluator, new InstructionEnum[]{InstructionEnum.Add, InstructionEnum.Sub, InstructionEnum.Mul, InstructionEnum.Div, InstructionEnum.Sqrt},
                        maxSizePopulation,
                        maxPopulationOverflow,
                        curpopularParents);

                double bestInRetries = 450000;
                ProgramResolution result = null;
                for (int retries = 0; retries < 20; retries++) {

                    result = iterator.iterate(curMaxRegisters, curMaxInstructions);

                    if (result.weight < bestInRetries)
                        bestInRetries = result.weight;
                }

                if (bestInRetries < winnerOfTheWorldWeight) {
                    winnerOfTheWorldWeight = bestInRetries;
                    winner = "#registers " + curMaxRegisters + " #maxinstructions " + curMaxInstructions  + " curpopularparents " + curpopularParents +" #maxpopulation " + maxSizePopulation + ' ' + result.instructions;
                    System.out.println(winner + " with " + winnerOfTheWorldWeight );
                }
            }

        }
        System.out.println(winner + " with " + winnerOfTheWorldWeight);

        //No solutions for 2 -> 5.
        //34242100000
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

}
