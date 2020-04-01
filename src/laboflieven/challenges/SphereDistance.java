package laboflieven.challenges;

import laboflieven.InOutParameters;
import laboflieven.ProgramFitnessExaminer;
import laboflieven.ReverseProgramIterator;
import laboflieven.statements.InstructionEnum;

import java.util.*;

public class SphereDistance
{
    public static double distance(double lat1, double lat2, double lon1, double lon2) {
        final int R = 1;
// Radius of the earth
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1; // convert to meters double height = el1 - el2; distance = Math.pow(distance, 2) + Math.pow(height, 2);
        return Math.sqrt(distance);
    }


    public static void main(String[] args)
    {
        InOutParameters io = new InOutParameters();
        int curMaxRegisters = 4;
        List<InOutParameters> collection = new ArrayList<>();
        collection.add(io.createParameter(io.fillDoubleArray(new double [] {2.0,-8.0,-24.0,0}, curMaxRegisters), distance(2.0,-8.0,-24.0,0),3, "R"));
        collection.add(io.createParameter(io.fillDoubleArray(new double [] {1.0, 2.0, 1.0,0}, curMaxRegisters), distance(1.0, 2.0, 1.0,0),3, "R"));// -1
        collection.add(io.createParameter(io.fillDoubleArray(new double [] {1.0, 2.0, -3.0,0}, curMaxRegisters), distance(1.0, 2.0, -3.0,0),3, "R"));
        collection.add(io.createParameter(io.fillDoubleArray(new double [] {1.0, -1, -56,0}, curMaxRegisters), distance(1.0, -1, -56,0),3, "R"));
        collection.add(io.createParameter(io.fillDoubleArray(new double [] {1.0, 2, -15,0}, curMaxRegisters), distance(1.0, 2.0, -15,0),3, "R"));
        collection.add(io.createParameter(io.fillDoubleArray(new double [] {1.0, -100, 2500,0}, curMaxRegisters), distance(1.0, -100, 2500,0),3, "R"));
        collection.add(io.createParameter(io.fillDoubleArray(new double [] {1.0, -200, 10000,0}, curMaxRegisters), distance(1.0, -200, 10000,0),3, "R"));
        collection.add(io.createParameter(io.fillDoubleArray(new double [] {1.0, -400, 40000,0}, curMaxRegisters), distance(1.0, -400, 40000,0),3, "R"));
        collection.add(io.createParameter(io.fillDoubleArray(new double [] {1.0, 500, 0,0}, curMaxRegisters), distance(1.0, 500, 0,0),3, "R"));
        collection.add(io.createParameter(io.fillDoubleArray(new double [] {1.0, 15000, 0,0}, curMaxRegisters), distance(1.0, 15000, 0,0),3, "R"));
        collection.add(io.createParameter(io.fillDoubleArray(new double [] {-1.0, 15000, 0,0}, curMaxRegisters), distance(-1.0, 15000, 0,0),3, "R"));
        collection.add(io.createParameter(io.fillDoubleArray(new double [] {2.0, 1000, 0,0}, curMaxRegisters), distance(2.0, 1000, 0,0),3, "R"));


        ProgramFitnessExaminer evaluator = new ProgramFitnessExaminer(collection);

        ReverseProgramIterator iter = new ReverseProgramIterator(evaluator, new InstructionEnum[]{InstructionEnum.Add, InstructionEnum.Sub, InstructionEnum.Mul, InstructionEnum.Div, InstructionEnum.Sqrt, InstructionEnum.Move, InstructionEnum.Sin, InstructionEnum.Cos});
        iter.iterate(curMaxRegisters, 10);

    }

}
