package laboflieven.programiterators;

import laboflieven.InstructionMark;
import laboflieven.Program;
import laboflieven.registers.Register;
import laboflieven.statements.*;

import java.util.*;

/**
 * Created by lveeckha on 31/05/2015.
 */
public class RealRandomProgramIterator {
    public int maximumInstructions = 12;
    public long counter = 0;

    public List<List<InstructionMark>> positiveSolutions = new ArrayList<>();
    private final RegularInstructionOpcodeEnum[] enums;
    private Register[] registers;
    private final InstructionFactoryInterface instructionFactory = new InstructionFactory();

    public RealRandomProgramIterator() {
        enums = RegularInstructionOpcodeEnum.values();
    }

    public RealRandomProgramIterator(RegularInstructionOpcodeEnum[] enums) {
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
        RegularInstructionOpcodeEnum instruction = enums[location];
        if (instruction.isDualRegister()) {
            Register register1 = registers[r.nextInt(registers.length)];
            Register register2 = registers[r.nextInt(registers.length)];
            InstructionMark actualInstruction = instructionFactory.createInstruction(new laboflieven.common.RegularInstructionOpcode(instruction), register1, register2);
            instructions.add(actualInstruction);

        } else {

            Register register1 = registers[r.nextInt(registers.length)];
            InstructionMark actualInstruction = instructionFactory.createInstruction(new laboflieven.common.RegularInstructionOpcode(instruction), register1);
            instructions.add(0, actualInstruction);
            /**
             * Available registers remains the same. No new registers are used.
             */

        }
        return recurse(instructions);
    }

    private boolean isUselessOp(RegularInstructionOpcodeEnum instruction, Register register1, Register register2) {
        return instruction == RegularInstructionOpcodeEnum.Move && register1.name.equals(register2.name);
    }


}
