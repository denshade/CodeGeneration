package laboflieven.statements;

import laboflieven.InstructionMark;
import laboflieven.common.InstructionSet;

import java.util.List;

public interface InstructionFactoryInterface
{
    InstructionMark generateRandomInstruction(List<Register> register);
    InstructionMark createInstruction(InstructionSet instructionEnum, Register... registers);

}
