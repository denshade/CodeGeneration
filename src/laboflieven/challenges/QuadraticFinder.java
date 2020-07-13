package laboflieven.challenges;

import laboflieven.*;
import laboflieven.examiners.ProgramFitnessExaminer;
import laboflieven.examiners.ProgramFitnessExaminerInterface;
import laboflieven.programiterators.RandomGeneticProgramIterator;
import laboflieven.programiterators.ReverseProgramIterator;
import laboflieven.runners.RegularStatementRunner;
import laboflieven.statements.RegularInstructionOpcode;
import laboflieven.statements.Register;

import java.util.*;

/**
 * Created by Lieven on 8/07/2015.
 */
public class QuadraticFinder implements InOutParameterSource, ProgramTemplate
{

    /**
     * //No solutions for 2 -> 5.
     *
     * @param args
     */
    public static void mainRandomized(String[] args)
    {
        RegularInstructionOpcode[] enums = {RegularInstructionOpcode.Add, RegularInstructionOpcode.Sub, RegularInstructionOpcode.Mul, RegularInstructionOpcode.Div, RegularInstructionOpcode.Sqrt, RegularInstructionOpcode.Move, RegularInstructionOpcode.Invert};
        List<InstructionMark> s = RandomGeneticProgramIterator.trySolutions(new QuadraticFinder(), enums, 1.1, 50000,100000,0.8,0.9,4,5);
        System.out.println("Winner with " + s);
    }

    private static double calcQuad(double[] args)
    {
        double a = args[0];
        double b = args[1];
        double c = args[2];
        return (-b + (Math.sqrt(b*b - 4*a*c))) / (2*a);
    }

    public static void main(String[] args) {//[r1 /= r2, Move r1 -> r4, r4 -= r3, Mul r4 -> r4, Invert r1, Sqrt r4, Sqrt r2, Sqrt r4, r3 -= r4, r3 /= r1, r1 += r4]
        List<InstructionMark> instructions = ProgramParser.parse("[r4 += r3, Mul r4 -> r1, Mul r3 -> r1, Mul r2 -> r3, r4 += r1, Sqrt r4, Invert r2, r2 += r1, Sqrt r4, r2 /= r4, r1 /= r2]"); //2.847396575786049
        List<InOutParameters> collection =  new QuadraticFinder().getInOutParameters(4);

        Register[] registers = Register.createRegisters(4, "R").toArray(new Register[0]);
        ProgramFitnessExaminerInterface evaluator = new ProgramFitnessExaminer(collection, new RegularStatementRunner());
        System.out.println(evaluator.calculateFitness(instructions, Arrays.asList(registers))); // 3.4334286175154967

        mainRandomized(args);
    }
    public static void reverseIterator(){
        int curMaxRegisters = 4;
        List<InOutParameters> collection = new QuadraticFinder().getInOutParameters(curMaxRegisters);
        ProgramFitnessExaminerInterface evaluator = new ProgramFitnessExaminer(collection, new RegularStatementRunner());

        ReverseProgramIterator iter = new ReverseProgramIterator(evaluator, new RegularInstructionOpcode[]{RegularInstructionOpcode.Add, RegularInstructionOpcode.Sub, RegularInstructionOpcode.Mul, RegularInstructionOpcode.Div, RegularInstructionOpcode.Sqrt, RegularInstructionOpcode.Move, RegularInstructionOpcode.Zero, RegularInstructionOpcode.One});
        iter.iterate(curMaxRegisters, 14);
    }

    public List<InOutParameters> getInOutParameters(int curMaxRegisters) {
        return TestCases.getTestCases(this, TestCases.getExampleInput4D(100,50),  4);
        /*InOutParameters io = new InOutParameters();
        List<InOutParameters> collection = new ArrayList<>();
        collection.add(io.createParameter(io.fillDoubleArray(new double [] {2.0,-8.0,-24.0}, curMaxRegisters), calcQuad(new double [] {2.0,-8.0,-24.0}),1));
        collection.add(io.createParameter(io.fillDoubleArray(new double [] {1.0, 2.0, 1.0}, curMaxRegisters), calcQuad(new double [] {1.0, 2.0, 1.0}),1));// -1
        collection.add(io.createParameter(io.fillDoubleArray(new double [] {1.0, 2.0, -3.0}, curMaxRegisters), calcQuad(new double [] {1.0, 2.0, -3.0}),1 ));
        collection.add(io.createParameter(io.fillDoubleArray(new double [] {1.0, -1, -56}, curMaxRegisters), calcQuad(new double [] {1.0, -1, -56}),1 ));
        collection.add(io.createParameter(io.fillDoubleArray(new double [] {1.0, 2, -15}, curMaxRegisters), calcQuad(new double [] {1.0, 2.0, -15}),1 ));
        collection.add(io.createParameter(io.fillDoubleArray(new double [] {1.0, -100, 2500}, curMaxRegisters), calcQuad(new double [] {1.0, -100, 2500}),1 ));
        collection.add(io.createParameter(io.fillDoubleArray(new double [] {1.0, -200, 10000}, curMaxRegisters), calcQuad(new double [] {1.0, -200, 10000}),1 ));
        collection.add(io.createParameter(io.fillDoubleArray(new double [] {1.0, -400, 40000}, curMaxRegisters), calcQuad(new double [] {1.0, -400, 40000}),1 ));
        collection.add(io.createParameter(io.fillDoubleArray(new double [] {1.0, 500, 0}, curMaxRegisters), calcQuad(new double [] {1.0, 500, 0}),1 ));
        collection.add(io.createParameter(io.fillDoubleArray(new double [] {1.0, 15000, 0}, curMaxRegisters), calcQuad(new double [] {1.0, 15000, 0}),1 ));
        collection.add(io.createParameter(io.fillDoubleArray(new double [] {-1.0, 15000, 0}, curMaxRegisters), calcQuad(new double [] {-1.0, 15000, 0}),1 ));
        collection.add(io.createParameter(io.fillDoubleArray(new double [] {2.0, 1000, 0}, curMaxRegisters), calcQuad(new double [] {2.0, 1000, 0}),1 ));
        collection.add(io.createParameter(io.fillDoubleArray(new double [] {100.0, 500, 0}, curMaxRegisters), calcQuad(new double [] {100.0, 500, 0}),1 ));
        collection.add(io.createParameter(io.fillDoubleArray(new double [] {1000.0, 50, 0}, curMaxRegisters), calcQuad(new double [] {1000.0, 50, 0}),1 ));

        return collection;*/
    }

    @Override
    public double run(double[] args) {
        return calcQuad(args);
    }
}
