package laboflieven;

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
        if (args.length != 1)
        {
            System.err.println("Usage : QuadraticFinder <maxInstructions>");
            System.exit(1);
        }
        int nrSolutions = Integer.parseInt(args[0]);
        List<InOutParameters> collection = new ArrayList<>();
        collection.add(createParameter(2.0,-8.0,-24.0,0.0, 6.0));
        collection.add(createParameter(1.0, 2.0, 1.0, 0.0, -1.0));
        collection.add(createParameter(1.0, -1, -56, 0.0, 8));
        collection.add(createParameter(1.0, 2, -15, 0.0, 3));
        ProgramEvaluator evaluator = new ProgramEvaluator(collection);
        //ReverseProgramIterator iterator = new ReverseProgramIterator(evaluator);
        RandomProgramIterator iterator = new RandomProgramIterator(evaluator);
        iterator.iterate(4, nrSolutions);
        //No solutions for 2 -> 5.
        //34242100000
    }

    private static InOutParameters createParameter(double a, double b, double c, double d, double result)
    {
        Map<String, Double> startParameters  = getMap(a,b,c,d);
        Map<String, Double> endParameters = new HashMap<>(1);
        endParameters.put("r3", result);
        InOutParameters parameters = new InOutParameters();
        parameters.input = startParameters;
        parameters.expectedOutput = endParameters;
        return parameters;
    }

    private static Map<String, Double> getMap(double a,double b,double c,double d)
    {
        Map<String, Double> results = new HashMap<>();
        results.put("r0", a);
        results.put("r1", b);
        results.put("r2", c);
        results.put("r3", d);
        return results;
    }


    private static InOutParameters createParameterSimple(double a, double b, double result)
    {
        Map<String, Double> startParameters = new HashMap<>();
        startParameters.put("r0", a);
        startParameters.put("r1", b);
        Map<String, Double> endParameters = new HashMap<>(1);
        endParameters.put("r1", result);
        InOutParameters parameters = new InOutParameters();
        parameters.input = startParameters;
        parameters.expectedOutput = endParameters;
        return parameters;
    }
}
