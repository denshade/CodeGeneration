package laboflieven.boardmoveinstructions;

import laboflieven.common.InstructionOpcode;
import laboflieven.statements.Register;

public abstract class BoardRegisterInstruction
{

    abstract public Integer execute(Register position, Board board, int ip);

    abstract public String toString();
    abstract public InstructionOpcode getInstructionOpcode();
}
