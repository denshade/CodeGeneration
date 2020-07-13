package laboflieven.programiterators;

import laboflieven.InstructionMark;
import laboflieven.Program;
import laboflieven.common.RegularInstructionOpcode;
import laboflieven.statements.*;

import java.util.*;

/**
 * Created by lveeckha on 31/05/2015.
 */
public class RealRandomProgramIterator {
    public int maximumInstructions = 12;
    public long counter = 0;

    public List<List<InstructionMark>> positiveSolutions = new ArrayList<>();
    private InstructionSet[] enums;
    private Register[] registers;
    private InstructionFactoryInterface instructionFactory = new InstructionFactory();

    public RealRandomProgramIterator() {
        enums = InstructionSet.values();
    }

    public RealRandomProgramIterator(InstructionSet[] enums) {
        this.enums = enums;
    }

    public Program getNextProgram(int numberOfRegisters, int maximumInstructions) {
        this.maximumInstructions = maximumInstructions;
        registers = new Register[numberOfRegisters];
        for (int i = 0; i < registers.length; i++) {
            registers[i] = new Register("r" + i);
        }
        Set<Register> availableRegisters = new HashSet<>();
        availableRegisters.add(registers[registers.length - 1]);// Add the result register.
        List<InstructionMark> instructions = recurse(new ArrayList<>());
        return new Program(instructions, Arrays.asList(registers));
    }

    public List<InstructionMark> recurse(List<InstructionMark> instructions) {
        if (instructions.size() >= maximumInstructions)
            return instructions;

        int instructionsLeft = maximumInstructions - instructions.size();
        if (instructionsLeft < 0) {
            return instructions;
        }

        Random r = new Random();
        int location = r.nextInt(enums.length);
        InstructionSet instruction = enums[location];
        if (instruction.isDualRegister()) {
            Register register1 = registers[r.nextInt(registers.length)];
            Register register2 = registers[r.nextInt(registers.length)];
            InstructionMark actualInstruction = instructionFactory.createInstruction(new RegularInstructionOpcode(instruction), register1, register2);
            instructions.add(actualInstruction);

        } else {

            Register register1 = registers[r.nextInt(registers.length)];
            InstructionMark actualInstruction = instructionFactory.createInstruction(new RegularInstructionOpcode(instruction), register1);
            instructions.add(0, actualInstruction);
            /**
             * Available registers remains the same. No new registers are used.
             */

        }
        return recurse(instructions);
    }

    private boolean isUselessOp(InstructionSet instruction, Register register1, Register register2) {
        return instruction == InstructionSet.Move && register1.name.equals(register2.name);
    }


}
