package laboflieven.challenges;

import laboflieven.InOutParameters;
import laboflieven.ProgramFitnessExaminer;
import laboflieven.ProgramFitnessExaminerInterface;
import laboflieven.programiterators.BruteForceProgramIterator;

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
        List<InOutParameters> collection = new ArrayList<>();
        collection.add(createParameter(2.0, 2.0));
        collection.add(createParameter(-15.0, 15.0));
        collection.add(createParameter(0.0, 0.0));
        ProgramFitnessExaminerInterface evaluator = new ProgramFitnessExaminer(collection);
        //ReverseProgramIterator iterator = new ReverseProgramIterator(evaluator);
        BruteForceProgramIterator iterator = new BruteForceProgramIterator(evaluator);
        iterator.iterate(1, nrSolutions);
        //No solutions for 2 -> 5.
        //34242100000
    }

    private static InOutParameters createParameter(double a, double result)
    {
        Map<String, Double> startParameters  = new HashMap<>();
        startParameters.put("r0", a);

        Map<String, Double> endParameters = new HashMap<>(1);
        endParameters.put("r0", result);

        InOutParameters parameters = new InOutParameters();
        parameters.input = startParameters;
        parameters.expectedOutput = endParameters;
        return parameters;
    }

}
