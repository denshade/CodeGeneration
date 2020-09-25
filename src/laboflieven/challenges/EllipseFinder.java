package laboflieven.challenges;

import laboflieven.InOutParameters;
import laboflieven.accinstructions.AccInstructionOpcodeEnum;
import laboflieven.accinstructions.InstructionFactory;
import laboflieven.common.Configuration;
import laboflieven.examiners.ProgramFitnessExaminer;
import laboflieven.examiners.ProgramFitnessExaminerInterface;
import laboflieven.humanresource.RandomGeneticProgramIterator;
import laboflieven.loggers.FitnessLogger;
import laboflieven.loggers.SysOutAccFitnessLogger;
import laboflieven.programiterators.GeneralBruteForceProgramIterator;
import laboflieven.programiterators.RandomProgramIterator;
import laboflieven.runners.AccStatementRunner;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EllipseFinder implements ProgramTemplate
{
    public static double distance(double a, double b) {
        double h = ((a-b)*(a-b)) / ((a+b) * (a+b));
        return Math.PI * (a + b) * ( 1+ ( 3*h) / (10 + Math.sqrt(4 - 3 * h)));
    }


    public static void main(String[] args) throws IOException {
        int nrInstructions = 30;
        if (args.length > 0) {
            nrInstructions = Integer.parseInt(args[0]);
        }
        System.out.println(distance(100,300));
        int curMaxRegisters = 2;
        List<InOutParameters> collection = TestCases.getTestCases(new EllipseFinder(), TestCases.getExampleInput2D(10000,100),curMaxRegisters);
        collection = differentiate(collection);

        ProgramFitnessExaminerInterface evaluator = new ProgramFitnessExaminer(collection, new AccStatementRunner());
        evaluator.addListener(new SysOutAccFitnessLogger(10000));

        Configuration config = Configuration.getInstance();
        config.setMaxNrInstructions(nrInstructions);
        config.setFitnessExaminer(evaluator);
        config.setInstructionFactory(new InstructionFactory());

        RandomProgramIterator iter = new RandomProgramIterator();
        long start = System.currentTimeMillis();
        iter.iterate(config);
        System.out.println(System.currentTimeMillis() - start + "ms");
    }

    private static List<InOutParameters> differentiate(List<InOutParameters> collection) {
        List<InOutParameters> result = new ArrayList<>();

        for (int i = 0; i < collection.size() - 1; i++)
        {
            InOutParameters param = new  InOutParameters();
            param.input =  collection.get(i).input;
            Map<String, Double> stringDoubleMap = new HashMap<String, Double>();
            Map<String, Double> currentValues = collection.get(i).expectedOutput;
            Map<String, Double> nextValues = collection.get(i + 1).expectedOutput;
            for (String r : currentValues.keySet())
            {
                stringDoubleMap.put(r,nextValues.get(r) - currentValues.get(r));
            }
            param.expectedOutput = stringDoubleMap;
            result.add(param);

        }
        return result;
    }

    @Override
    public double run(double[] args) {
        return distance(args[0], args[1]);
    }
}
