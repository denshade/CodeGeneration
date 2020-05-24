package laboflieven.challenges;

import laboflieven.*;
import laboflieven.recursionheuristics.AlwaysRecursionHeuristic;
import laboflieven.recursionheuristics.NoInvertedHeuristic;
import laboflieven.statements.Instruction;
import laboflieven.accinstructions.InstructionEnum;
import laboflieven.statements.Register;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class P1 implements ProgramTemplate
{
    /*
    Found a program: [R2 /= R1, Mod R3 -> R1, Mod R2 -> R3, Nand R3 -> R1]
Found a program: [R3 /= R1, Mod R2 -> R1, Mod R3 -> R1, Nand R1 -> R1]
Found a program: [R3 /= R1, Mod R2 -> R1, Mod R3 -> R1, Nand R2 -> R1]
Found a program: [R3 /= R1, Mod R2 -> R1, Mod R3 -> R1, Nand R3 -> R1]
Found a program: [R3 /= R1, Mod R2 -> R1, Mod R3 -> R2, Nand R2 -> R1]

     */
    public static void main(String[] args) throws IOException {
        int curMaxRegisters = 3;
        List<double[]> points = new ArrayList<>();
        for (int i = 1; i < 40; i++) {
            points.add(new double[] { i,3,5});
        }
        List<InOutParameters> collection = TestCases.getTestCases(new P1(), points.toArray(new double[0][0]),curMaxRegisters);
        AccProgramFitnessExaminer evaluator = new AccProgramFitnessExaminer(collection);
        AccBruteForceProgramIterator iter = new AccBruteForceProgramIterator(evaluator,  InstructionEnum.values());
        long start = System.currentTimeMillis();
        iter.iterate(curMaxRegisters, 6);
        //evaluator.writeAndClose();
        System.out.println(System.currentTimeMillis() - start + "ms");

        //mainT(21,3);
    }

    @Override
    public double run(double[] args) {
        if (args[0] % 3 == 0) return 1;
        if (args[0] % 5 == 0) return 1;
        return 0;
    }

    public static void mainT(double R1, double R2) {
        double left, right = 0;

        left = R1;
        left = left + right;
        right = R2;
        left = left % right;
        left = nand(left, right);
        R1 = left;
        System.out.println(R1);
    }

    static double nand(double left, double right) {
        boolean sourceB = left < 0.0000001 ? false: true;
        boolean destinationB = right < 0.0000001 ? false: true;
        boolean evaluation = !(sourceB && destinationB);
        return evaluation?1.0:0.0;
    }

}
