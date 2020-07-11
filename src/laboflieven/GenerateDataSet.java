package laboflieven;

import laboflieven.programiterators.RealRandomProgramIterator;
import laboflieven.runners.RegularStatementRunner;
import laboflieven.runners.StatementRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Lieven on 19-5-2017.
 */
public class GenerateDataSet
{
    public static void main(String[] args)
    {
        RealRandomProgramIterator iterator = new RealRandomProgramIterator();
        Program program = iterator.getNextProgram(4, 10);
        System.out.println(program);
        List<InOutParameters> collection = new ArrayList<>();
        collection.add(createParameter(2.0,-8.0,-24.0,0.0, 6.0));
        collection.add(createParameter(1.0, 2.0, 1.0, 0.0, -1.0));
        collection.add(createParameter(1.0, -1, -56, 0.0, 8));
        collection.add(createParameter(1.0, 2, -15, 0.0, 3));
        StatementRunner runner = new RegularStatementRunner();
        Map<String, Double> map = getMap(2.0, -8.0, -24.0, 0.0);
        runner.execute(program, map);
        System.out.println(map);
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
}
