package laboflieven.challenges;

import laboflieven.*;
import laboflieven.examiners.ProgramFitnessExaminer;
import laboflieven.examiners.ProgramFitnessExaminerInterface;
import laboflieven.programiterators.RandomGeneticProgramIterator;
import laboflieven.runners.RegularStatementRunner;
import laboflieven.statements.RegularInstructionOpcodeEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AtanFinder
{
    public static double distance(double x, double y) {
        return Math.atan2(x,y);
    }

    private static double[] fillDoubleArray(double[] original, int newSize)
    {
        double[] result = new double[newSize];
        System.arraycopy(original, 0, result, 0, original.length);
        return result;
    }

    private static TestcaseInOutParameters createParameter(double[] doubles, double result)
    {
        return TestcaseInOutParameters.createParameter(doubles, result, 1);
    }

    public static void main(String[] args)
    {
        int curMaxRegisters = 4;
        List<TestcaseInOutParameters> collection = new ArrayList<>();
        for (double x = Math.PI/2 * -1; x < Math.PI/2; x += 0.1)
        {
            for (double y = Math.PI/2 * -1; y < Math.PI/2; y += 0.1)
            {
                collection.add(createParameter(fillDoubleArray(new double [] {x,y}, curMaxRegisters), distance(x,y)));
            }

        }
        ProgramFitnessExaminerInterface evaluator = new ProgramFitnessExaminer(collection, new RegularStatementRunner());
        RandomGeneticProgramIterator iter = new RandomGeneticProgramIterator(evaluator, RegularInstructionOpcodeEnum.values(),
                10000, 1.3, 0.4);

        iter.iterate(curMaxRegisters, 15);

    }

}
