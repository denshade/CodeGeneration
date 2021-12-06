package laboflieven.statements;

import laboflieven.InstructionMark;
import laboflieven.common.InstructionOpcode;

import java.util.List;

public interface InstructionFactoryInterface
{
    InstructionMark generateRandomInstruction(List<Register> register);
    InstructionMark generateRandomInstruction(List<Register> register, Object[] enums);
    InstructionMark createInstruction(InstructionOpcode instructionEnum, Register... registers);

}
