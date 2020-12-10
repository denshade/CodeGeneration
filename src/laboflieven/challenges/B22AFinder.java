package laboflieven.challenges;

import laboflieven.TestcaseInOutParameters;
import laboflieven.InstructionMark;
import laboflieven.accinstructions.*;
import laboflieven.examiners.AccumulatorProgramFitnessExaminer;
import laboflieven.examiners.ProgramFitnessExaminerInterface;
import laboflieven.runners.AccStatementRunner;
import laboflieven.statements.Register;
import java.util.ArrayList;
import java.util.List;

public class B22AFinder implements ProgramTemplate
{
    public static double distance(double a, double b) {
        return b * b +  2 * a ;
    }


    public static void main(String[] args)  {
        int curMaxRegisters = 2;
        List<TestcaseInOutParameters> collection = TestCases.getTestCases(new B22AFinder(), TestCases.getExampleInput2D(50,10),curMaxRegisters);

        ProgramFitnessExaminerInterface evaluator = new AccumulatorProgramFitnessExaminer(collection, new AccStatementRunner());
        List<Register> registers = Register.createRegisters(2, "R");
        List<InstructionMark> instructions = new ArrayList<>();
        System.out.println(evaluator.calculateFitness(instructions, registers));
        instructions.add(new LoadIntoLeftAcc(registers.get(1)));
        System.out.println(evaluator.calculateFitness(instructions, registers));
        instructions.add(new LoadIntoRightAcc(registers.get(1)));
        System.out.println(evaluator.calculateFitness(instructions, registers));
        instructions.add(new Mul());
        System.out.println(evaluator.calculateFitness(instructions, registers));
        instructions.add(new LoadAccLeftIntoRegister(registers.get(1)));
        System.out.println(evaluator.calculateFitness(instructions, registers));
        instructions.add(new LoadIntoLeftAcc(registers.get(0)));
        System.out.println(evaluator.calculateFitness(instructions, registers));
        instructions.add(new LoadIntoRightAcc(registers.get(0)));
        System.out.println(evaluator.calculateFitness(instructions, registers));
        instructions.add(new Add());
        System.out.println(evaluator.calculateFitness(instructions, registers));
        instructions.add(new LoadIntoRightAcc(registers.get(1)));
        System.out.println(evaluator.calculateFitness(instructions, registers));
        instructions.add(new Add());
        System.out.println(evaluator.calculateFitness(instructions, registers));
        instructions.add(new LoadAccLeftIntoRegister(registers.get(0)));
        System.out.println(evaluator.calculateFitness(instructions, registers));

        /*iter = new BruteForceProgramIterator(evaluator, new AlwaysRecursionHeuristic());
        start = System.currentTimeMillis();
        iter.iterate(curMaxRegisters, 4);
        //evaluator.writeAndClose();
        */
        System.out.println( "19000ms");

    }

    @Override
    public double run(double[] args) {
        return distance(args[0], args[1]);
    }
}
