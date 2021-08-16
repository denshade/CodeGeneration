package laboflieven.challenges;

import laboflieven.TestcaseInOutParameters;
import laboflieven.accinstructions.AccInstructionOpcodeEnum;
import laboflieven.accinstructions.InstructionFactory;
import laboflieven.common.Configuration;
import laboflieven.examiners.AccumulatorProgramFitnessExaminer;
import laboflieven.examiners.ProgramFitnessExaminer;
import laboflieven.examiners.ProgramFitnessExaminerInterface;
import laboflieven.loggers.TimingAccFitnessLogger;
import laboflieven.programiterators.BruteForceProgramIterator;
import laboflieven.programiterators.GeneralBruteForceProgramIterator;
import laboflieven.programiterators.RandomProgramIterator;
import laboflieven.recursionheuristics.NoInvertedHeuristic;
import laboflieven.recursionheuristics.RecursionHeuristic;
import laboflieven.runners.AccStatementRunner;
import laboflieven.runners.RegularStatementRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Lieven on 8/07/2015.
 */
public class N3P1Finder {

    /**
     * //No solutions for 2 -> 5.
     *
     * @param args
     */
    public static void main(String[] args)
    {
        if (args.length != 1)
        {
            System.err.println("Usage : "+ N3P1Finder.class+" <maxInstructions>");
            System.exit(1);
        }
        int maxInstructions = Integer.parseInt(args[0]);
        List<TestcaseInOutParameters> collection = new ArrayList<>();
        for (int i = 1; i < 100; i++) {
            collection.add(createParameter(i, func(i)));
        }
        ProgramFitnessExaminerInterface evaluator = new AccumulatorProgramFitnessExaminer(collection, new AccStatementRunner());
        evaluator.addListener(new TimingAccFitnessLogger(1000));
        var conf = new Configuration();
        conf.setHeuristic(new NoInvertedHeuristic());
        conf.setMaxNrInstructions(maxInstructions);
        conf.setFitnessExaminer(evaluator);
        conf.setInstructionFactory(new InstructionFactory());
        conf.setNumberOfRegisters(1);
        conf.setAccOperations( AccInstructionOpcodeEnum.values());

        RandomProgramIterator iterator = new RandomProgramIterator();
        iterator.iterate(conf);
        //No solutions for 2 -> 5.
        //34242100000
    }

    private static double func(int x) {
        int cur = x;
        int counter = 0;
        while ( cur > 1) {
            counter++;
            if (cur % 2 == 0 ) cur/=2;
            else {
                cur = cur * 3 + 1;
            }
        }
        return counter;
    }

    private static TestcaseInOutParameters createParameter(double a, double result)
    {
        Map<String, Double> startParameters  = new HashMap<>();
        startParameters.put("R1", a);

        Map<String, Double> endParameters = new HashMap<>(1);
        endParameters.put("R1", result);

        TestcaseInOutParameters parameters = new TestcaseInOutParameters();
        parameters.input = startParameters;
        parameters.expectedOutput = endParameters;
        return parameters;
    }

}
