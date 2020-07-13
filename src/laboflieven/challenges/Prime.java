package laboflieven.challenges;

import laboflieven.examiners.ProgramFitnessExaminer;
import laboflieven.examiners.ProgramFitnessExaminerInterface;
import laboflieven.programiterators.AccRandomGeneticProgramIterator;
import laboflieven.InOutParameters;
import laboflieven.accinstructions.AccInstructionOpcode;
import laboflieven.runners.AccStatementRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Prime implements ProgramTemplate
{
    /*
    Found a program: [R2 /= R1, Mod R3 -> R1, Mod R2 -> R3, Nand R3 -> R1]
Found a program: [R3 /= R1, Mod R2 -> R1, Mod R3 -> R1, Nand R1 -> R1]
Found a program: [R3 /= R1, Mod R2 -> R1, Mod R3 -> R1, Nand R2 -> R1]
Found a program: [R3 /= R1, Mod R2 -> R1, Mod R3 -> R1, Nand R3 -> R1]
Found a program: [R3 /= R1, Mod R2 -> R1, Mod R3 -> R2, Nand R2 -> R1]

     */
    public static void main(String[] args) throws IOException {

        int curMaxRegisters = 4;
        List<double[]> points = new ArrayList<>();

        for (int i = 2; i < 40; i++) {
            points.add(new double[] { i, isPrime(i)});
        }
        List<InOutParameters> collection = TestCases.getTestCases(new Prime(), points.toArray(new double[0][0]),curMaxRegisters);
        ProgramFitnessExaminerInterface evaluator = new ProgramFitnessExaminer(collection,new AccStatementRunner());
        AccRandomGeneticProgramIterator iter = new AccRandomGeneticProgramIterator(evaluator,  AccInstructionOpcode.anyExcept(Set.of(AccInstructionOpcode.Sqrt, AccInstructionOpcode.JumpIfGteStart, AccInstructionOpcode.JumpIfLteStart,
                AccInstructionOpcode.Log)), 1000,1.2,0.4);
        long start = System.currentTimeMillis();
        System.out.println(iter.iterate(curMaxRegisters, 20));
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
