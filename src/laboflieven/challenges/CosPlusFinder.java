package laboflieven.challenges;

import laboflieven.InOutParameters;
import laboflieven.LoggingProgramFitnessExaminer;
import laboflieven.ReverseProgramIterator;
import laboflieven.statements.InstructionEnum;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class CosPlusFinder implements ProgramTemplate
{
    public static double distance(double lat1, double lat2, double lon1, double lon2) {
        return Math.cos(lat1) + Math.sin(lat2) + Math.cos(lon1) + Math.sin(lon2);
    }


    public static void main(String[] args) throws IOException {
        int curMaxRegisters = 4;
        List<InOutParameters> collection = TestCases.getTestCases(new CosPlusFinder(), TestCases.getExampleInput4D(50,10),4);


        File f = new File("c:\\temp\\test.csv");
        LoggingProgramFitnessExaminer evaluator = new LoggingProgramFitnessExaminer(f, collection);
        ReverseProgramIterator iter = new ReverseProgramIterator(evaluator, new InstructionEnum[]{InstructionEnum.Add, InstructionEnum.Sub, InstructionEnum.Mul, InstructionEnum.Div, InstructionEnum.Sqrt, InstructionEnum.Move, InstructionEnum.Sin, InstructionEnum.Cos});
        iter.iterate(curMaxRegisters, 4);
        evaluator.writeAndClose();

    }

    @Override
    public double run(double[] args) {
        return distance(args[0], args[1], args[2], args[3]);
    }
}
