package laboflieven.statements;

import laboflieven.InstructionMark;
import laboflieven.accinstructions.AccRegisterInstruction;
import laboflieven.accinstructions.InstructionEnum;
import laboflieven.common.EnumWrapper;

import java.util.List;

public interface InstructionFactoryInterface
{
    InstructionMark generateRandomInstruction(List<Register> register);
    InstructionMark createInstruction(EnumWrapper instructionEnum, Register... registers);

}
