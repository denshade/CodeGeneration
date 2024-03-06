package laboflieven.instructions.boardmoveinstructions;

import laboflieven.common.InstructionOpcode;
import laboflieven.registers.Register;

public abstract class BoardRegisterInstruction
{

    abstract public Integer execute(Register position, Board board, int ip);

    abstract public String toString();
    abstract public InstructionOpcode getInstructionOpcode();
}
