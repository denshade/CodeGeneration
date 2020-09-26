package laboflieven.challenges;

import laboflieven.*;
import laboflieven.accinstructions.AccInstructionOpcodeEnum;
import laboflieven.common.Configuration;
import laboflieven.examiners.LoggingProgramFitnessExaminer;
import laboflieven.examiners.ProgramFitnessExaminer;
import laboflieven.examiners.ProgramFitnessExaminerInterface;
import laboflieven.loggers.BitmapFitnessLogger;
import laboflieven.loggers.FitnessLogger;
import laboflieven.programiterators.*;
import laboflieven.programiterators.ReverseProgramIterator;
import laboflieven.runners.AccStatementRunner;
import laboflieven.runners.RegularStatementRunner;
import laboflieven.statements.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Lieven on 8/07/2015.
 */
public class FormulaFinder {

    /**
     * //No solutions for 2 -> 5.
     *
     * @param args
     */
    public static void mainRndGenetic(String[] args)
    {
        double maxPopulationOverflow = 1.1;
        //double popularParents = .8;
        double[][] doubles = {new double[]{ 10, 1, 1}, new double[]{ 1, 10, 1}, new double[]{ 1, 1, 10},
                new double[]{ 0, 0, 0}, new double[]{ 1, 100, 1}, new double[]{ 1, 1, 100},
                new double[]{ 1000, 50, 1}, new double[]{ 1000, 1, 50}, new double[]{ 50, 1, 1000},
                new double[]{ 10000, 50, 10}, new double[]{ 10000, -1, 50}, new double[]{ -10000, -100, 1000}

        };

        double winnerOfTheWorldWeight = 45000;
        String winner = "";
        for (int maxSizePopulation = 50000; maxSizePopulation < 100000; maxSizePopulation += 10000)
        for (double curpopularParents = 0.8; curpopularParents < 0.9; curpopularParents+= 0.2)
        for (int curMaxRegisters = 3; curMaxRegisters < 4; curMaxRegisters++)
        {

            List<InOutParameters> collection = new ArrayList<>();
            for (double[] doubleRow : doubles)
            {
                if (!Double.isNaN(simulateFormula(doubleRow)))
                    collection.add(createParameter(fillDoubleArray(doubleRow, curMaxRegisters), simulateFormula(doubleRow)));
            }


            ProgramFitnessExaminerInterface evaluator = new ProgramFitnessExaminer(collection, new RegularStatementRunner());

            for (int curMaxInstructions = 4; curMaxInstructions < 7; curMaxInstructions++) {

                RandomGeneticProgramIterator iterator = new RandomGeneticProgramIterator(evaluator, new RegularInstructionOpcodeEnum[]{RegularInstructionOpcodeEnum.Add, RegularInstructionOpcodeEnum.Sub, RegularInstructionOpcodeEnum.Mul, RegularInstructionOpcodeEnum.Div, RegularInstructionOpcodeEnum.Sqrt},
                        maxSizePopulation,
                        maxPopulationOverflow,
                        curpopularParents);

                double bestInRetries = 450000;
                ProgramResolution result = null;
                for (int retries = 0; retries < 200; retries++) {

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

    private static double simulateFormula(double[] args)
    {
/*        double a = args[0];
        double b = args[1];
        double c = args[2]; // c *= a;[c = c*a] b*=b;[b=b²] a += a [a=2*a]; a = 4*a; b²-
        return (a - b) / 2*a;*/

        double a = args[0];
        return Math.sqrt(a);


//        return (a - b) / 2*a;
        //b*b - 4 a c. Mul r2 -> r0, Mul r1 -> r1, r0+= r0, r0+= r0, Sub r1 -> r0, r3+= r0]
        // Math.sqrt(b*b - 4*a*c);
//        double c = args[2]; // c *= a;[c = c*a] b*=b;[b=b²] a += a [a=2*a]; a = 4*a; b²-
       // return Math.log(a)/Math.log(b);

        //b*b - 4ac = Mul r2 -> r0, Mul r1 -> r1, r0+= r0, r0+= r0, Sub r1 -> r0, r3+= r0]
        //return Math.sqrt(b*b - 4*a*c);
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
        endParameters.put("R1", result);
        InOutParameters parameters = new InOutParameters();
        parameters.input = startParameters;
        parameters.expectedOutput = endParameters;
        return parameters;
    }


    private static Map<String, Double> getMap(double[] doubles)
    {
        Map<String, Double> results = new HashMap<>();
        for (int l = 1; l <= doubles.length; l++)
        {
            results.put("R"+l, doubles[l - 1]);
        }
        return results;
    }

    public static void mainReverseProgramIterator(String[] args)
    {
        int curMaxRegisters = 3;
        double[][] doubles = {new double[]{ 10, 1, 1}, new double[]{ 1, 10, 1}, new double[]{ 1, 1, 10},
                new double[]{ 0, 0, 0}, new double[]{ 1, 100, 1}, new double[]{ 1, 1, 100},
                new double[]{ 1000, 50, 1}, new double[]{ 1000, 1, 50}, new double[]{ 50, 1, 1000},
                new double[]{ 10000, 50, 10}, new double[]{ 10000, -1, 50}, new double[]{ -10000, -100, 1000}

        };
        List<InOutParameters> collection = new ArrayList<>();
        for (double[] doubleRow : doubles)
        {
            if (!Double.isNaN(simulateFormula(doubleRow)))
                collection.add(createParameter(fillDoubleArray(doubleRow, curMaxRegisters), simulateFormula(doubleRow)));
        }

        ProgramFitnessExaminerInterface evaluator = new ProgramFitnessExaminer(collection, new RegularStatementRunner());

        ReverseProgramIterator iter = new ReverseProgramIterator(evaluator, new RegularInstructionOpcodeEnum[]{RegularInstructionOpcodeEnum.Add, RegularInstructionOpcodeEnum.Sub, RegularInstructionOpcodeEnum.Mul, RegularInstructionOpcodeEnum.Div, RegularInstructionOpcodeEnum.Sqrt, RegularInstructionOpcodeEnum.Move, RegularInstructionOpcodeEnum.Log});
        iter.iterate(curMaxRegisters, 3);

    }
    public static void main(String[] args)
    {
        try {
            mainBruteAcc(args);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void mainRnd(String[] args) throws IOException {
        int curMaxRegisters = 3;
        double[][] doubles = {new double[]{ 10, 1, 1}, new double[]{ 1, 10, 1}, new double[]{ 1, 1, 10},
                new double[]{ 0, 0, 0}, new double[]{ 1, 100, 1}, new double[]{ 1, 1, 100},
                new double[]{ 1000, 50, 1}, new double[]{ 1000, 1, 50}, new double[]{ 50, 1, 1000},
                new double[]{ 10000, 50, 10}, new double[]{ 10000, -1, 50}, new double[]{ -10000, -100, 1000}

        };
        List<InOutParameters> collection = new ArrayList<>();
        for (double[] doubleRow : doubles)
        {
            if (!Double.isNaN(simulateFormula(doubleRow)))
                collection.add(createParameter(fillDoubleArray(doubleRow, curMaxRegisters), simulateFormula(doubleRow)));
        }

        ProgramFitnessExaminerInterface evaluator = new LoggingProgramFitnessExaminer(new File("logs.csv"), collection, new RegularStatementRunner());
        Configuration configuration = Configuration.getInstance();
        configuration.setMaxNrInstructions(6);
        configuration.setFitnessExaminer(evaluator);
        configuration.setInstructionOpcodes(new RegularInstructionOpcodeEnum[]{RegularInstructionOpcodeEnum.Add, RegularInstructionOpcodeEnum.Sub, RegularInstructionOpcodeEnum.Mul, RegularInstructionOpcodeEnum.Div, RegularInstructionOpcodeEnum.Sqrt, RegularInstructionOpcodeEnum.Move, RegularInstructionOpcodeEnum.Log});

        ProgramIterator iter = new RandomProgramIterator();
        iter.iterate(configuration);

    }

    public static void mainBrute(String[] args)
    {
        int curMaxRegisters = 3;
        double[][] doubles = {new double[]{ 10, 1, 1}, new double[]{ 1, 10, 1}, new double[]{ 1, 1, 10},
                new double[]{ 0, 0, 0}, new double[]{ 1, 100, 1}, new double[]{ 1, 1, 100},
                new double[]{ 1000, 50, 1}, new double[]{ 1000, 1, 50}, new double[]{ 50, 1, 1000},
                new double[]{ 10000, 50, 10}, new double[]{ 10000, -1, 50}, new double[]{ -10000, -100, 1000}

        };
        List<InOutParameters> collection = new ArrayList<>();
        for (double[] doubleRow : doubles)
        {
            if (!Double.isNaN(simulateFormula(doubleRow)))
                collection.add(createParameter(fillDoubleArray(doubleRow, curMaxRegisters), simulateFormula(doubleRow)));
        }

        ProgramFitnessExaminerInterface evaluator = new ProgramFitnessExaminer(collection, new RegularStatementRunner());
        BruteForceProgramIterator iter = new BruteForceProgramIterator(evaluator, new RegularInstructionOpcodeEnum[]{RegularInstructionOpcodeEnum.Add, RegularInstructionOpcodeEnum.Sub, RegularInstructionOpcodeEnum.Mul, RegularInstructionOpcodeEnum.Div, RegularInstructionOpcodeEnum.Sqrt, RegularInstructionOpcodeEnum.Move, RegularInstructionOpcodeEnum.Log});
        iter.iterate(curMaxRegisters, 5);
    }

    public static void mainBruteWithBmpLogging(String[] args) throws IOException {
        /*int curMaxRegisters = 3;
        double[][] doubles = {new double[]{ 10, 1, 1}, new double[]{ 1, 10, 1}, new double[]{ 1, 1, 10},
                new double[]{ 0, 0, 0}, new double[]{ 1, 100, 1}, new double[]{ 1, 1, 100},
                new double[]{ 1000, 50, 1}, new double[]{ 1000, 1, 50}, new double[]{ 50, 1, 1000},
                new double[]{ 10000, 50, 10}, new double[]{ 10000, -1, 50}, new double[]{ -10000, -100, 1000}

        };*/
        int curMaxRegisters = 2;
        double[][] doubles = {new double[]{ 10, 1}, new double[]{ 1, 10}, new double[]{ 1, 1},
                new double[]{ 0, 0}, new double[]{ 1, 100},
                new double[]{ 1000, 50}, new double[]{ 1000, 1}, new double[]{ 50, 1},
                new double[]{ 10000, 50}, new double[]{ 10000, -1}, new double[]{ -10000, -100}

        };

        List<InOutParameters> collection = new ArrayList<>();
        for (double[] doubleRow : doubles)
        {
            if (!Double.isNaN(simulateFormula(doubleRow)) && !Double.isInfinite(simulateFormula(doubleRow)))
                collection.add(createParameter(fillDoubleArray(doubleRow, curMaxRegisters), simulateFormula(doubleRow)));
        }
        RegularInstructionOpcodeEnum[] enums = RegularInstructionOpcodeEnum.values();
        //InstructionEnum[] enums = new InstructionEnum[]{InstructionEnum.Add, InstructionEnum.Sub, InstructionEnum.Mul, InstructionEnum.Div, InstructionEnum.Sqrt, InstructionEnum.Move, InstructionEnum.Log};
        //enums = new InstructionEnum[]{InstructionEnum.Add, InstructionEnum.Sub, InstructionEnum.Mul, InstructionEnum.Div, InstructionEnum.Log};
        FitnessLogger logger = new BitmapFitnessLogger(new java.io.File("hello.bmp"), enums.length, curMaxRegisters );
        ProgramFitnessExaminerInterface evaluator = new ProgramFitnessExaminer(collection, new RegularStatementRunner());
        evaluator.addListener(logger);
        BruteForceProgramIterator iter = new BruteForceProgramIterator(evaluator, enums);
        iter.iterate(curMaxRegisters, 3);
        ((BitmapFitnessLogger) logger).finish();
    }

    public static void mainBruteAcc(String[] args) throws IOException {
        /*int curMaxRegisters = 3;
        double[][] doubles = {new double[]{ 10, 1, 1}, new double[]{ 1, 10, 1}, new double[]{ 1, 1, 10},
                new double[]{ 0, 0, 0}, new double[]{ 1, 100, 1}, new double[]{ 1, 1, 100},
                new double[]{ 1000, 50, 1}, new double[]{ 1000, 1, 50}, new double[]{ 50, 1, 1000},
                new double[]{ 10000, 50, 10}, new double[]{ 10000, -1, 50}, new double[]{ -10000, -100, 1000}

        };*/
        int curMaxRegisters = 2;
        double[][] doubles = {new double[]{ 10, 1}, new double[]{ 1, 10}, new double[]{ 1, 1},
                new double[]{ 0, 0}, new double[]{ 1, 100},
                new double[]{ 1000, 50}, new double[]{ 1000, 1}, new double[]{ 50, 1},
                new double[]{ 10000, 50}, new double[]{ 10000, -1}, new double[]{ -10000, -100}

        };

        List<InOutParameters> collection = new ArrayList<>();
        for (double[] doubleRow : doubles)
        {
            if (!Double.isNaN(simulateFormula(doubleRow)) && !Double.isInfinite(simulateFormula(doubleRow)))
                collection.add(createParameter(fillDoubleArray(doubleRow, curMaxRegisters), simulateFormula(doubleRow)));
        }
        //laboflieven.accinstructions.InstructionEnum[] enums = laboflieven.accinstructions.InstructionEnum.values();
        AccInstructionOpcodeEnum[] enums = new AccInstructionOpcodeEnum[] {
                AccInstructionOpcodeEnum.Log,
                AccInstructionOpcodeEnum.Div,
                AccInstructionOpcodeEnum.AccLeftPull,
                AccInstructionOpcodeEnum.AccRightPull,
                AccInstructionOpcodeEnum.AccLeftPush,
                AccInstructionOpcodeEnum.AccRightPush
        };
        //enums = new InstructionEnum[]{InstructionEnum.Add, InstructionEnum.Sub, InstructionEnum.Mul, InstructionEnum.Div, InstructionEnum.Log};
        ProgramFitnessExaminer evaluator = new ProgramFitnessExaminer(collection, new AccStatementRunner());
        GeneralBruteForceProgramIterator iter = new GeneralBruteForceProgramIterator(evaluator, enums);
        iter.iterate(curMaxRegisters, 10);
    }


}
