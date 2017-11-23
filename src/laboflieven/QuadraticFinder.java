package laboflieven;

import laboflieven.statements.InstructionEnum;

import java.util.*;

/**
 * Created by Lieven on 8/07/2015.
 */
public class QuadraticFinder {

    /**
     * //No solutions for 2 -> 5.
     *
     * @param args
     */
    public static void main(String[] args)
    {
        double maxPopulationOverflow = 1.1;
        //double popularParents = .8;

        double winnerOfTheWorldWeight = 45000;
        String winner = "";
        for (int maxSizePopulation = 50000; maxSizePopulation < 100000; maxSizePopulation += 10000)
        for (double curpopularParents = 0.8; curpopularParents < 0.9; curpopularParents+= 0.2)
        for (int curMaxRegisters = 4; curMaxRegisters < 8; curMaxRegisters++)
        {

            List<InOutParameters> collection = new ArrayList<>();
            collection.add(createParameter(fillDoubleArray(new double [] {2.0,-8.0,-24.0}, curMaxRegisters), 6.0));
            collection.add(createParameter(fillDoubleArray(new double [] {1.0, 2.0, 1.0}, curMaxRegisters), -1.0));
            collection.add(createParameter(fillDoubleArray(new double [] {1.0, -1, -56}, curMaxRegisters), 8));
            collection.add(createParameter(fillDoubleArray(new double [] {1.0, 2, -15}, curMaxRegisters), 3));
            collection.add(createParameter(fillDoubleArray(new double [] {1.0, -100, 2500}, curMaxRegisters), 50));
            collection.add(createParameter(fillDoubleArray(new double [] {1.0, -200, 10000}, curMaxRegisters), 100));
            collection.add(createParameter(fillDoubleArray(new double [] {1.0, -400, 40000}, curMaxRegisters), 200));



            ProgramFitnessExaminer evaluator = new ProgramFitnessExaminer(collection);

            for (int curMaxInstructions = 5; curMaxInstructions < 15; curMaxInstructions++) {

                RandomGeneticProgramIterator iterator = new RandomGeneticProgramIterator(evaluator, new InstructionEnum[]{InstructionEnum.Add, InstructionEnum.Sub, InstructionEnum.Mul, InstructionEnum.Div, InstructionEnum.Sqrt},
                        maxSizePopulation,
                        maxPopulationOverflow,
                        curpopularParents);

                double bestInRetries = 450000;
                for (int retries = 0; retries < 20; retries++) {

                    double result = iterator.iterate(curMaxRegisters, curMaxInstructions);
                    if (result < bestInRetries)
                        bestInRetries = result;
                }

                if (bestInRetries < winnerOfTheWorldWeight) {
                    winnerOfTheWorldWeight = bestInRetries;
                    winner = "#registers " + curMaxRegisters + " #maxinstructions " + curMaxInstructions  + " curpopularparents " + curpopularParents +" #maxpopulation " + maxSizePopulation;
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
