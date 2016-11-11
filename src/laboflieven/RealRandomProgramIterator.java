package laboflieven;

import laboflieven.statements.Instruction;
import laboflieven.statements.InstructionEnum;
import laboflieven.statements.InstructionFactory;
import laboflieven.statements.Register;

import java.util.*;

/**
 * Created by lveeckha on 31/05/2015.
 */
public class RealRandomProgramIterator {
    public int maximumInstructions = 12;
    public long counter = 0;

    public List<List<Instruction>> positiveSolutions = new ArrayList<>();
    private InstructionEnum[] enums;
    private Register[] registers;

    public RealRandomProgramIterator() {
        enums = InstructionEnum.values();
    }

    public RealRandomProgramIterator(InstructionEnum[] enums) {
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
        List<Instruction> instructions = recurse(new ArrayList<>());
        return new Program(instructions, Arrays.asList(registers));
    }

    public List<Instruction> recurse(List<Instruction> instructions) {
        if (instructions.size() >= maximumInstructions)
            return instructions;

        int instructionsLeft = maximumInstructions - instructions.size();
        if (instructionsLeft < 0) {
            return instructions;
        }

        Random r = new Random();
        int location = r.nextInt(enums.length);
        InstructionEnum instruction = enums[location];
        if (instruction.isDualRegister()) {
            Register register1 = registers[r.nextInt(registers.length)];
            Register register2 = registers[r.nextInt(registers.length)];
            Instruction actualInstruction = InstructionFactory.createInstruction(instruction, register1, register2);
            instructions.add(actualInstruction);

        } else {

            Register register1 = registers[r.nextInt(registers.length)];
            Instruction actualInstruction = InstructionFactory.createInstruction(instruction, register1);
            instructions.add(0, actualInstruction);
            /**
             * Available registers remains the same. No new registers are used.
             */

        }
        return recurse(instructions);
    }

    private boolean isUselessOp(InstructionEnum instruction, Register register1, Register register2) {
        return instruction == InstructionEnum.Move && register1.name.equals(register2.name);
    }


}
