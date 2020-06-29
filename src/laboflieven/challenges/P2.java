package laboflieven.challenges;

import laboflieven.examiners.AccProgramFitnessExaminer;
import laboflieven.programiterators.AccRandomGeneticProgramIterator;
import laboflieven.InOutParameters;
import laboflieven.accinstructions.InstructionEnum;
import laboflieven.loggers.SysOutAccFitnessLogger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class P2 implements ProgramTemplate
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
        for (int i = 1; i < 10; i++) {
            points.add(new double[] { i, Math.sqrt(5),0,0});
        }
        List<InOutParameters> collection = TestCases.getTestCases(new P2(), points.toArray(new double[0][0]),curMaxRegisters);
        AccProgramFitnessExaminer evaluator = new AccProgramFitnessExaminer(collection);
        evaluator.addListener(new SysOutAccFitnessLogger(10000));
        AccRandomGeneticProgramIterator iter = new AccRandomGeneticProgramIterator(evaluator,  InstructionEnum.values(), 10000,1.2,0.4);
        long start = System.currentTimeMillis();
        System.out.println(iter.iterate(curMaxRegisters, 15));
        //evaluator.writeAndClose();
        System.out.println(System.currentTimeMillis() - start + "ms");

        //mainT(15,3);
    }

    @Override
    public double run(double[] args) {
        return F((int)args[0]);
    }

    public long F(int n)
    {
        if (n  == 0) return 1;
        if (n  == 1) return 2;
        return F(n - 1 ) + F(n - 2);
    }


}
