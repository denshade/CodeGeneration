package laboflieven.statements;

import laboflieven.InstructionMark;

import java.util.List;

public interface InstructionFactoryInterface
{
    InstructionMark generateRandomInstruction(List<Register> register);
}
