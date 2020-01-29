package laboflieven.challenges;

import laboflieven.*;
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
    public static void mainRandomized(String[] args)
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
            collection.add(createParameter(fillDoubleArray(new double [] {2.0,-8.0,-24.0}, curMaxRegisters), calcQuad(new double [] {2.0,-8.0,-24.0})));
            collection.add(createParameter(fillDoubleArray(new double [] {1.0, 2.0, 1.0}, curMaxRegisters), calcQuad(new double [] {1.0, 2.0, 1.0})));// -1
            collection.add(createParameter(fillDoubleArray(new double [] {1.0, 2.0, -3.0}, curMaxRegisters), calcQuad(new double [] {1.0, 2.0, -3.0})));
            collection.add(createParameter(fillDoubleArray(new double [] {1.0, -1, -56}, curMaxRegisters), calcQuad(new double [] {1.0, -1, -56})));
            collection.add(createParameter(fillDoubleArray(new double [] {1.0, 2, -15}, curMaxRegisters), calcQuad(new double [] {1.0, 2.0, -15})));
            collection.add(createParameter(fillDoubleArray(new double [] {1.0, -100, 2500}, curMaxRegisters), calcQuad(new double [] {1.0, -100, 2500})));
            collection.add(createParameter(fillDoubleArray(new double [] {1.0, -200, 10000}, curMaxRegisters), calcQuad(new double [] {1.0, -200, 10000})));
            collection.add(createParameter(fillDoubleArray(new double [] {1.0, -400, 40000}, curMaxRegisters), calcQuad(new double [] {1.0, -400, 40000})));
            collection.add(createParameter(fillDoubleArray(new double [] {1.0, 500, 0}, curMaxRegisters), calcQuad(new double [] {1.0, 500, 0})));
            collection.add(createParameter(fillDoubleArray(new double [] {1.0, 15000, 0}, curMaxRegisters), calcQuad(new double [] {1.0, 15000, 0})));
            collection.add(createParameter(fillDoubleArray(new double [] {-1.0, 15000, 0}, curMaxRegisters), calcQuad(new double [] {-1.0, 15000, 0})));
            collection.add(createParameter(fillDoubleArray(new double [] {2.0, 1000, 0}, curMaxRegisters), calcQuad(new double [] {2.0, 1000, 0})));



            ProgramFitnessExaminer evaluator = new ProgramFitnessExaminer(collection);

            for (int curMaxInstructions = 15; curMaxInstructions < 20; curMaxInstructions++) {

                RandomGeneticProgramIterator iterator = new RandomGeneticProgramIterator(evaluator, InstructionEnum.values(), //);// new InstructionEnum[]{InstructionEnum.Add, InstructionEnum.Sub, InstructionEnum.Mul, InstructionEnum.Div, InstructionEnum.Sqrt},
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
        collection.add(createParameter(fillDoubleArray(new double [] {2.0,-8.0,-24.0}, curMaxRegisters), calcQuad(new double [] {2.0,-8.0,-24.0})));
        collection.add(createParameter(fillDoubleArray(new double [] {1.0, 2.0, 1.0}, curMaxRegisters), calcQuad(new double [] {1.0, 2.0, 1.0})));// -1
        collection.add(createParameter(fillDoubleArray(new double [] {1.0, 2.0, -3.0}, curMaxRegisters), calcQuad(new double [] {1.0, 2.0, -3.0})));
        collection.add(createParameter(fillDoubleArray(new double [] {1.0, -1, -56}, curMaxRegisters), calcQuad(new double [] {1.0, -1, -56})));
        collection.add(createParameter(fillDoubleArray(new double [] {1.0, 2, -15}, curMaxRegisters), calcQuad(new double [] {1.0, 2.0, -15})));
        collection.add(createParameter(fillDoubleArray(new double [] {1.0, -100, 2500}, curMaxRegisters), calcQuad(new double [] {1.0, -100, 2500})));
        collection.add(createParameter(fillDoubleArray(new double [] {1.0, -200, 10000}, curMaxRegisters), calcQuad(new double [] {1.0, -200, 10000})));
        collection.add(createParameter(fillDoubleArray(new double [] {1.0, -400, 40000}, curMaxRegisters), calcQuad(new double [] {1.0, -400, 40000})));
        collection.add(createParameter(fillDoubleArray(new double [] {1.0, 500, 0}, curMaxRegisters), calcQuad(new double [] {1.0, 500, 0})));
        collection.add(createParameter(fillDoubleArray(new double [] {1.0, 15000, 0}, curMaxRegisters), calcQuad(new double [] {1.0, 15000, 0})));
        collection.add(createParameter(fillDoubleArray(new double [] {-1.0, 15000, 0}, curMaxRegisters), calcQuad(new double [] {-1.0, 15000, 0})));
        collection.add(createParameter(fillDoubleArray(new double [] {2.0, 1000, 0}, curMaxRegisters), calcQuad(new double [] {2.0, 1000, 0})));


        ProgramFitnessExaminer evaluator = new ProgramFitnessExaminer(collection);

        ReverseProgramIterator iter = new ReverseProgramIterator(evaluator, new InstructionEnum[]{InstructionEnum.Add, InstructionEnum.Sub, InstructionEnum.Mul, InstructionEnum.Div, InstructionEnum.Sqrt, InstructionEnum.Move});
        iter.iterate(curMaxRegisters, 14);

    }

}