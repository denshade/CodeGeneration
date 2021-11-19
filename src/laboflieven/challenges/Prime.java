package laboflieven.challenges;

import laboflieven.accinstructions.AccInstructionOpcodeEnumBuilder;
import laboflieven.examiners.ProgramFitnessExaminer;
import laboflieven.examiners.ProgramFitnessExaminerInterface;
import laboflieven.programiterators.AccRandomGeneticProgramIterator;
import laboflieven.TestcaseInOutParameters;
import laboflieven.accinstructions.AccInstructionOpcodeEnum;
import laboflieven.runners.AccStatementRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Prime implements ProgramTemplate
{

    public static void main(String[] args) {

        int curMaxRegisters = 4;
        List<double[]> points = new ArrayList<>();

        for (int i = 2; i < 40; i++) {
            points.add(new double[] { i, isPrime(i)});
        }
        List<TestcaseInOutParameters> collection = TestCases.getTestCases(new Prime(), points.toArray(new double[0][0]),curMaxRegisters);
        ProgramFitnessExaminerInterface evaluator = new ProgramFitnessExaminer(collection,new AccStatementRunner());
        AccRandomGeneticProgramIterator iter = new AccRandomGeneticProgramIterator(evaluator,
                AccInstructionOpcodeEnumBuilder.make().anyExcept(Set.of(AccInstructionOpcodeEnum.Sqrt, AccInstructionOpcodeEnum.JumpIfGteStart, AccInstructionOpcodeEnum.JumpIfLteStart,
                AccInstructionOpcodeEnum.Log)).build(), 1000,1.2,0.4);
        long start = System.currentTimeMillis();
        System.out.println(iter.iterate(curMaxRegisters, 4));
        //evaluator.writeAndClose();
        System.out.println(System.currentTimeMillis() - start + "ms");

        //mainT(15,3);
    }
    private static double isPrime(int i)
    {
        for (int k = 2; k < i; k++)
        {
            if (i % k == 0) return 0;
        }
        return 1;
    }

    @Override
    public double run(double[] args) {
        if (args[0] % 3 == 0) return 1;
        if (args[0] % 5 == 0) return 1;
        return 0;
    }


}
