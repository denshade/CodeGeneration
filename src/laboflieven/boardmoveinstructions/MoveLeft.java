package laboflieven.boardmoveinstructions;

import laboflieven.accinstructions.AccInstructionOpcodeEnum;
import laboflieven.common.InstructionOpcode;
import laboflieven.registers.Register;

public class MoveLeft extends BoardRegisterInstruction
{

    public Integer execute(Register position, Board board, int ip){return null;}

    public Integer execute(Position currentPosition, Board board, int ip)
    {
        if (currentPosition.x - 1 < 0) throw new ImpossibleMoveException();

        currentPosition.x--;
        return null;
    }

    public String toString()
    {
        return  "left = left + right";
    }
    @Override
    public InstructionOpcode getInstructionOpcode() {
        return new laboflieven.common.AccInstructionOpcode(AccInstructionOpcodeEnum.Add);
    }
}
