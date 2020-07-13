package laboflieven.programiterators;

import laboflieven.InstructionMark;
import laboflieven.common.RegularInstructionOpcode;
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
    private InstructionEnum[] enums;
    private Register[] registers;
    private int numberOfRegisters;
    private List<InstructionMark> bestSolution;
    private double bestScore = 1000;
    private InstructionFactoryInterface instructionFactory = new InstructionFactory();

    public RandomProgramIterator(ProgramFitnessExaminerInterface evaluator) {
        this.evaluator = evaluator;
        enums = InstructionEnum.values();
    }

    public RandomProgramIterator(ProgramFitnessExaminerInterface evaluator, InstructionEnum[] enums) {
        this.evaluator = evaluator;

        this.enums = enums;
    }

    public void iterate(int numberOfRegisters, int maximumInstructions) {
        this.numberOfRegisters = numberOfRegisters;
        this.maximumInstructions = maximumInstructions;
        registers = new Register[numberOfRegisters];
        for (int i = 0; i < registers.length; i++) {
            registers[i] = new Register("r" + i);
        }
        Set<Register> availableRegisters = new HashSet<>();
        availableRegisters.add(registers[registers.length - 1]);// Add the result register.
        while (true) {
            recurse(new ArrayList<>());
        }
    }

    public void recurse(List<InstructionMark> instructions) {
        if (instructions.size() >= maximumInstructions)
            return;

        int instructionsLeft = maximumInstructions - instructions.size();
        if (instructionsLeft < 0) {
            return;
        }

        Random r = new Random();
        int location = r.nextInt(enums.length);
        InstructionEnum instruction = enums[location];
        if (instruction.isDualRegister()) {
            Register register1 = registers[r.nextInt(registers.length)];
            Register register2 = registers[r.nextInt(registers.length)];

            if (isUselessOp(instruction, register1, register2)) {
                return;
            }

            InstructionMark actualInstruction = instructionFactory.createInstruction(new RegularInstructionOpcode(instruction), register1, register2);
            instructions.add(actualInstruction);
            eval(instructions, Arrays.asList(registers));
            recurse(instructions);
            instructions.remove(0);
        } else {

            Register register1 = registers[r.nextInt(registers.length)];
            InstructionMark actualInstruction = instructionFactory.createInstruction(new RegularInstructionOpcode(instruction), register1);
            instructions.add(0, actualInstruction);
            eval(instructions, Arrays.asList(registers));
            /**
             * Available registers remains the same. No new registers are used.
             */
            recurse(instructions);
            instructions.remove(0);

        }
    }

    private boolean isUselessOp(InstructionEnum instruction, Register register1, Register register2) {
        return instruction == InstructionEnum.Move && register1.name.equals(register2.name);
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
