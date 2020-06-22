package laboflieven.statements;

import junit.framework.TestCase;
import laboflieven.programiterators.BruteForceProgramIterator;
import laboflieven.InOutParameters;
import laboflieven.ProgramFitnessExaminer;
import laboflieven.ProgramFitnessExaminerInterface;

import java.util.*;

/**
 * Created by Lieven on 6/07/2015.
 */
public class BruteForceProgramIteratorTest extends TestCase {

    public void testIterate() throws Exception {
        ProgramFitnessExaminerInterface ev = new ProgramFitnessExaminer(Collections.EMPTY_LIST);
        BruteForceProgramIterator iterator = new BruteForceProgramIterator(ev);
        iterator.iterate(4, 2);

    }

    private static InOutParameters createParameter(double a, double b, double c, double d, double result)
    {
        Map<String, Double> startParameters  = getMap(a,b,c,d);
        Map<String, Double> endParameters = new HashMap<>(1);
        endParameters.put("R3", result);
        InOutParameters parameters = new InOutParameters();
        parameters.input = startParameters;
        parameters.expectedOutput = endParameters;
        return parameters;
    }

    private static Map<String, Double> getMap(double a,double b,double c,double d)
    {
        Map<String, Double> results = new HashMap<>();
        results.put("R1", a);
        results.put("R2", b);
        results.put("R3", c);
        results.put("R4", d);
        return results;
    }

    public void testQuadratic()
    {
        List<InOutParameters> collection = new ArrayList<>();
        collection.add(createParameter(2.0,-8.0,-24.0,0.0, 6.0));
        collection.add(createParameter(1.0, 2.0, 1.0, 0.0, -1.0));
        collection.add(createParameter(1.0, -1, -56, 0.0, 8));
        collection.add(createParameter(1.0, 2, -15, 0.0, 3));
        ProgramFitnessExaminerInterface evaluator = new ProgramFitnessExaminer(collection);
        BruteForceProgramIterator iterator = new BruteForceProgramIterator(evaluator);
        int maximumInstructions = 1;
        iterator.iterate(4, maximumInstructions);
        System.out.println(iterator.counter);
        System.out.println(iterator.positiveSolutions.size());
    }

}