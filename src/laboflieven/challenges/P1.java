package laboflieven.challenges;

import laboflieven.*;
import laboflieven.examiners.ProgramFitnessExaminer;
import laboflieven.loggers.AccPctBruteForceFitnessLogger;
import laboflieven.accinstructions.AccInstructionSet;
import laboflieven.programiterators.GeneralBruteForceProgramIterator;
import laboflieven.recursionheuristics.AccHeuristic;
import laboflieven.runners.AccStatementRunner;

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
        ProgramFitnessExaminer evaluator = new ProgramFitnessExaminer(collection,new AccStatementRunner());
        AccInstructionSet[] instructions = new AccInstructionSet[] {
                AccInstructionSet.AccRightPush, AccInstructionSet.AccRightPush, AccInstructionSet.AccRightPull, AccInstructionSet.AccLeftPull, AccInstructionSet.Mod, AccInstructionSet.Mod,
                AccInstructionSet.Nand
        };
        evaluator.addListener(new AccPctBruteForceFitnessLogger(instructions, 10000, curMaxRegisters));
        GeneralBruteForceProgramIterator iter = new GeneralBruteForceProgramIterator(evaluator, instructions, new AccHeuristic());
        //AccRandomGeneticProgramIterator iter = new AccRandomGeneticProgramIterator(evaluator,  InstructionEnum.getMinimal(), 1000,1.2,0.4);
        long start = System.currentTimeMillis();
        System.out.println(iter.iterate(curMaxRegisters, 8));
        //evaluator.writeAndClose();
        System.out.println(System.currentTimeMillis() - start + "ms");

        //mainT(15,3);
    }

    @Override
    public double run(double[] args) {
        if (args[0] % 3 == 0) return 1;
        if (args[0] % 5 == 0) return 1;
        return 0;
    }


}
