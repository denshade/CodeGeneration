package laboflieven.programiterators;

import laboflieven.InstructionMark;
import laboflieven.accinstructions.AccLeftPull;
import laboflieven.accinstructions.AccLeftPush;
import laboflieven.common.BestFitRegister;
import laboflieven.examiners.ProgramFitnessExaminerInterface;
import laboflieven.StoppedByUserException;
import laboflieven.statements.*;

import java.util.*;

/**
 * Created by lveeckha on 31/05/2015.
 */
public class RandomProgramIterator {
    public int maximumInstructions = 12;
    public long counter = 0;

    public List<List<InstructionMark>> positiveSolutions = new ArrayList<>();
    private ProgramFitnessExaminerInterface evaluator;
    private RegularInstructionOpcodeEnum[] enums;
    private Register[] registers;
    private int numberOfRegisters;
    private List<InstructionMark> bestSolution;
    private double bestScore = 1000;
    public InstructionFactoryInterface instructionFactory = new InstructionFactory();

    public RandomProgramIterator(ProgramFitnessExaminerInterface evaluator) {
        this.evaluator = evaluator;
        enums = RegularInstructionOpcodeEnum.values();
    }

    public RandomProgramIterator(ProgramFitnessExaminerInterface evaluator, RegularInstructionOpcodeEnum[] enums) {
        this.evaluator = evaluator;

        this.enums = enums;
    }

    public void iterate(int numberOfRegisters, int maximumInstructions) {
        this.numberOfRegisters = numberOfRegisters;
        this.maximumInstructions = maximumInstructions;
        registers = Register.createRegisters(numberOfRegisters, "R").toArray(new Register[0]);
        Set<Register> availableRegisters = new HashSet<>();
        availableRegisters.add(registers[registers.length - 1]);// Add the result register.
        while (true) {
            recurse(new ArrayList<>(List.of(new AccLeftPush(registers[0]))));
        }
    }

    public void recurse(List<InstructionMark> instructions) {
        if (instructions.size() >= maximumInstructions)
            return;
        if (instructions.size() == maximumInstructions - 1)
        {
            instructions.add(new AccLeftPull(registers[0]));
            eval(instructions, Arrays.asList(registers));
            instructions.remove(instructions.size()-1);
        }

        int instructionsLeft = maximumInstructions - instructions.size();
        if (instructionsLeft < 0) {
            return;
        }
        InstructionMark actualInstruction = instructionFactory.generateRandomInstruction(Arrays.asList(registers));
        instructions.add(actualInstruction);
        eval(instructions, Arrays.asList(registers));
        recurse(instructions);
        instructions.remove(instructions.size()-1);

    }

    private boolean isUselessOp(RegularInstructionOpcodeEnum instruction, Register register1, Register register2) {
        return instruction == RegularInstructionOpcodeEnum.Move && register1.name.equals(register2.name);
    }

    private void eval(List<InstructionMark> instructions, List<Register> registers) {
        if (instructions.size() != maximumInstructions) return;
        double val =  evaluator.calculateFitness(instructions, registers);
        if (val < bestScore)
        {
            bestScore = val;
            bestSolution = instructions;
            System.out.println( evaluator.calculateFitness(instructions, registers) + "," + difference(instructions) );
        }
        if (instructions.size() == maximumInstructions && evaluator.isFit(instructions, registers)) {
            positiveSolutions.add(new ArrayList<>(instructions));
            System.out.println("Found a program! " + positiveSolutions);
            throw new StoppedByUserException();
        }

    }

    public static int difference(List<InstructionMark> instructions) {
        Map<String, Integer> counter = new HashMap<>();
        for (InstructionMark instruction : instructions)
        {
            counter.putIfAbsent(instruction.getClass().toString(), 0);
            Integer count = counter.get(instruction.getClass().toString());
            counter.put(instruction.getClass().toString(), count + 1);
        }
        return counter.keySet().size();
    }

}
