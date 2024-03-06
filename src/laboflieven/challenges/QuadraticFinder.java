package laboflieven.challenges;

import laboflieven.*;
import laboflieven.examiners.ProgramFitnessExaminer;
import laboflieven.examiners.ProgramFitnessExaminerInterface;
import laboflieven.programiterators.RandomGeneticProgramIterator;
import laboflieven.programiterators.ReverseProgramIterator;
import laboflieven.registers.NumberNamingScheme;
import laboflieven.runners.RegularStatementRunner;
import laboflieven.instructions.regular.RegularInstructionOpcodeEnum;
import laboflieven.registers.Register;

import java.util.*;

/**
 * Created by Lieven on 8/07/2015.
 */
public class QuadraticFinder implements InOutParameterSource, ProgramTemplate
{

    /**
     * //No solutions for 2 -> 5.
     */
    public static void mainRandomized(String[] args)
    {
        RegularInstructionOpcodeEnum[] enums = {RegularInstructionOpcodeEnum.Add, RegularInstructionOpcodeEnum.Sub, RegularInstructionOpcodeEnum.Mul, RegularInstructionOpcodeEnum.Div, RegularInstructionOpcodeEnum.Sqrt, RegularInstructionOpcodeEnum.Move, RegularInstructionOpcodeEnum.Invert};
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
        List<TestcaseInOutParameters> collection =  new QuadraticFinder().getInOutParameters(4);

        Register[] registers = new NumberNamingScheme().createRegisters(4).toArray(new Register[0]);
        ProgramFitnessExaminerInterface evaluator = new ProgramFitnessExaminer(collection, new RegularStatementRunner());
        System.out.println(evaluator.calculateFitness(instructions, Arrays.asList(registers))); // 3.4334286175154967

        mainRandomized(args);
    }
    public static void reverseIterator(){
        int curMaxRegisters = 4;
        List<TestcaseInOutParameters> collection = new QuadraticFinder().getInOutParameters(curMaxRegisters);
        ProgramFitnessExaminerInterface evaluator = new ProgramFitnessExaminer(collection, new RegularStatementRunner());

        ReverseProgramIterator iter = new ReverseProgramIterator(evaluator, new RegularInstructionOpcodeEnum[]{RegularInstructionOpcodeEnum.Add, RegularInstructionOpcodeEnum.Sub, RegularInstructionOpcodeEnum.Mul, RegularInstructionOpcodeEnum.Div, RegularInstructionOpcodeEnum.Sqrt, RegularInstructionOpcodeEnum.Move, RegularInstructionOpcodeEnum.Zero, RegularInstructionOpcodeEnum.One});
        iter.iterate(curMaxRegisters, 14);
    }

    public List<TestcaseInOutParameters> getInOutParameters(int curMaxRegisters) {
        return new TestCases().getAllTestCases(this, TestCases.getExampleInput4D(100,50),  4);
    }

    @Override
    public double run(double[] args) {
        return calcQuad(args);
    }
}
