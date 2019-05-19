package laboflieven;

import laboflieven.statements.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class ProgramFitnessExaminerTest {

    private static double calcQuad(double[] args)
    {
        double a = args[0];
        double b = args[1];
        double c = args[2]; // c *= a;[c = c*a] b*=b;[b=b²] a += a [a=2*a]; a = 4*a; b²-
        return (b - c) / (2 * a);

        //b*b - 4ac = Mul r2 -> r0, Mul r1 -> r1, r0+= r0, r0+= r0, Sub r1 -> r0, r3+= r0]
        // Math.sqrt(b*b - 4*a*c);
    }

    @Test
    public void calculateFitness() {
        double[][] doubles = {new double[]{ 10, 1, 1}, new double[]{ 1, 10, 1}, new double[]{ 1, 1, 10},
                new double[]{ 0, 0, 0}, new double[]{ 1, 100, 1}, new double[]{ 1, 1, 100},
                new double[]{ 1000, 50, 1}, new double[]{ 1000, 1, 50}, new double[]{ 50, 1, 1000},
                new double[]{ 10000, 50, 10}, new double[]{ 10000, -1, 50}, new double[]{ -10000, -100, 1000}

        };
        List<InOutParameters> collection = new ArrayList<>();
        for (double[] doubleRow : doubles)
        {
            if (!Double.isNaN(calcQuad(doubleRow)))
                collection.add(createParameter(fillDoubleArray(doubleRow, 3), calcQuad(doubleRow)));
        }
        ProgramFitnessExaminer evaluator = new ProgramFitnessExaminer(collection);
        Register r1 = new Register("r0");
        Register r2 = new Register("r1");
        Register r3 = new Register("r2");
        List<Register> registers = new ArrayList<>();
        registers.add(r1);
        registers.add(r2);
        registers.add(r3);
        List<Instruction> instructions = new ArrayList<>();
        instructions.add(new Add(r1,r1));
        instructions.add(new Sub(r3,r2));
        instructions.add(new Div(r1,r2));
        instructions.add(new Move(r2,r1));

        assertEquals(0, evaluator.calculateFitness(instructions, registers),0.01);
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
        endParameters.put("r0", result);
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

}