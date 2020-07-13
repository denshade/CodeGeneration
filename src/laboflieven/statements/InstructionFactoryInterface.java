package laboflieven.statements;

import laboflieven.InstructionMark;
import laboflieven.common.InstructionOpcode;

import java.util.List;

public interface InstructionFactoryInterface
{
    InstructionMark generateRandomInstruction(List<Register> register);
    InstructionMark createInstruction(InstructionOpcode instructionEnum, Register... registers);

}
