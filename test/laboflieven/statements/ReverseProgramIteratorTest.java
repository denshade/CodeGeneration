package laboflieven;

import junit.framework.TestCase;

import java.util.*;

/**
 * Created by Lieven on 28/06/2015.
 */
public class ReverseProgramIteratorTest extends TestCase {

    public void testMainHard() throws Exception {
        List<InOutParameters> collection = new ArrayList<>();
        collection.add(createParameter(2.0,-8.0,-24.0,0.0, 6.0));
        collection.add(createParameter(1.0, 2.0, 1.0, 0.0, -1.0));
        collection.add(createParameter(1.0, -1, -56, 0.0, 8));
        collection.add(createParameter(1.0, 2, -15, 0.0, 3));
        ProgramFitnessExaminer evaluator = new ProgramFitnessExaminer(collection);
        ReverseProgramIterator iterator = new ReverseProgramIterator(evaluator);
        iterator.iterate(4, 2);

        assertEquals(0, iterator.positiveSolutions.size());
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

    public void testMainEasy() throws Exception {
        ProgramFitnessExaminer evaluator = new ProgramFitnessExaminer(Collections.singletonList(createParameterSimple(1, 2, 3)));
        ReverseProgramIterator iterator = new ReverseProgramIterator(evaluator);
        iterator.iterate(2, 1);
        assertEquals(1, iterator.positiveSolutions.size());
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