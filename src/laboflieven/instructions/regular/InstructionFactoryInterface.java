package laboflieven.instructions.regular;

import laboflieven.InstructionMark;
import laboflieven.common.InstructionOpcode;
import laboflieven.registers.Register;

import java.util.ArrayList;
import java.util.List;

public interface InstructionFactoryInterface
{
    default List<InstructionMark> generateRandomInstruction(List<Register> registers, int nrInstructions) {
        List<InstructionMark> instructions = new ArrayList<>(nrInstructions);
        for (int i = 0; i < nrInstructions; i++) {
            instructions.add(generateRandomInstruction(registers));
        }
        return instructions;
    }
    InstructionMark generateRandomInstruction(List<Register> register);
    InstructionMark generateRandomInstruction(List<Register> register, Object[] enums);
    InstructionMark createInstruction(InstructionOpcode instructionEnum, Register... registers);

}
