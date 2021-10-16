package laboflieven.challenges;

import laboflieven.TestcaseInOutParameters;
import laboflieven.examiners.ProgramFitnessExaminer;
import laboflieven.examiners.ProgramFitnessExaminerInterface;
import laboflieven.programiterators.BruteForceProgramIterator;
import laboflieven.runners.RegularStatementRunner;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Lieven on 8/07/2015.
 */
public class AbsFinder {

    /**
     * //No solutions for 2 -> 5.
     *
     * @param args
     */
    public static void main(String[] args)
    {
        if (args.length != 1)
        {
            System.err.println("Usage : "+AbsFinder.class+" <maxInstructions>");
            System.exit(1);
        }
        int nrSolutions = Integer.parseInt(args[0]);
        List<TestcaseInOutParameters> collection = new ArrayList<>();
        collection.add(createParameter(2.0, 2.0));
        collection.add(createParameter(-15.0, 15.0));
        collection.add(createParameter(0.0, 0.0));
        ProgramFitnessExaminerInterface evaluator = new ProgramFitnessExaminer(collection, new RegularStatementRunner());
        BruteForceProgramIterator iterator = new BruteForceProgramIterator(evaluator);
        iterator.iterate(1, nrSolutions);
    }

    private static TestcaseInOutParameters createParameter(double a, double result)
    {
        return TestcaseInOutParameters.createParameter(new double[]{a}, result, 1);
    }

}
