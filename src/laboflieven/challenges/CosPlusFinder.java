package laboflieven.challenges;

import laboflieven.InOutParameters;
import laboflieven.LoggingProgramFitnessExaminer;
import laboflieven.ProgramFitnessExaminer;
import laboflieven.ReverseProgramIterator;
import laboflieven.statements.InstructionEnum;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class CosPlusFinder implements ProgramTemplate
{
    public static double distance(double lat1, double lat2) {
        return Math.cos(lat1 + lat2) ;
    }


    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        int curMaxRegisters = 2;
        List<InOutParameters> collection = TestCases.getTestCases(new CosPlusFinder(), TestCases.getExampleInput2D(50,10),2);


        File f = new File("c:\\temp\\test.csv");
        ProgramFitnessExaminer evaluator = new ProgramFitnessExaminer(collection);
        ReverseProgramIterator iter = new ReverseProgramIterator(evaluator, new InstructionEnum[]{InstructionEnum.Add, InstructionEnum.Sub, InstructionEnum.Mul, InstructionEnum.Div, InstructionEnum.Sqrt, InstructionEnum.Sin, InstructionEnum.PI/*, InstructionEnum.Cos*/});
        iter.iterate(curMaxRegisters, 8);
        //evaluator.writeAndClose();
        System.out.println(System.currentTimeMillis() - start + "ms");
    }

    @Override
    public double run(double[] args) {
        return distance(args[0], args[1]);
    }
}
