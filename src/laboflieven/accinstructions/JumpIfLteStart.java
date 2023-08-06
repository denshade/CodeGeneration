package laboflieven.accinstructions;

import laboflieven.common.InstructionOpcode;
import laboflieven.registers.Register;

public class JumpIfLteStart  extends NoRegisterInstruction
{


    public Integer execute(Register left, Register right, int ip)
    {
        if (left.value <= right.value)
        {
            return 0;
        }
        return null;
    }

    @Override
    public String toString() {
        return " if left <=  R then goto 0 ";
    }
    @Override
    public InstructionOpcode getInstructionOpcode() {
        return new laboflieven.common.AccInstructionOpcode(AccInstructionOpcodeEnum.JumpIfLteStart);
    }
}
