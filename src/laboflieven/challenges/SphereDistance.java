package laboflieven.challenges;

import laboflieven.InOutParameters;
import laboflieven.examiners.ProgramFitnessExaminer;
import laboflieven.loggers.FileFitnessLogger;
import laboflieven.programiterators.ReverseProgramIterator;
import laboflieven.runners.RegularStatementRunner;
import laboflieven.statements.RegularInstructionOpcodeEnum;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class SphereDistance implements ProgramTemplate
{
    public static double distance(double lat1, double lat2, double lon1, double lon2) {
        final int R = 1;
// Radius of the earth
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1; // convert to meters double height = el1 - el2; distance = Math.pow(distance, 2) + Math.pow(height, 2);*/
        return Math.sqrt(distance);
    }


    public static void main(String[] args) throws IOException {
        InOutParameters io = new InOutParameters();
        int curMaxRegisters = 4;
        List<InOutParameters> collection = TestCases.getTestCases(new SphereDistance(), TestCases.getExampleInput4D(50,10),4);


        File f = new File("c:\\temp\\test.csv");
        ProgramFitnessExaminer evaluator = new ProgramFitnessExaminer(collection, new RegularStatementRunner());
        var logger = new FileFitnessLogger(f);
        evaluator.addListener(logger);
        ReverseProgramIterator iter = new ReverseProgramIterator(evaluator, new RegularInstructionOpcodeEnum[]{RegularInstructionOpcodeEnum.Add, RegularInstructionOpcodeEnum.Sub, RegularInstructionOpcodeEnum.Mul, RegularInstructionOpcodeEnum.Div, RegularInstructionOpcodeEnum.Sqrt, RegularInstructionOpcodeEnum.Move, RegularInstructionOpcodeEnum.Sin, RegularInstructionOpcodeEnum.Cos});
        iter.iterate(curMaxRegisters, 4);
        logger.finish();
    }

    @Override
    public double run(double[] args) {
        return distance(args[0], args[1], args[2], args[3]);
    }
}
