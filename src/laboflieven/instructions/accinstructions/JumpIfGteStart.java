package laboflieven.instructions.accinstructions;

import laboflieven.common.InstructionOpcode;
import laboflieven.registers.Register;

public class JumpIfGteStart  extends NoRegisterInstruction
{


    public Integer execute(Register left, Register right, int ip)
    {
        if (left.value >= right.value)
        {
            return 0;
        }
        return null;
    }

    @Override
    public String toString() {
        return " Jump to start if L >= R ";
    }
    @Override
    public InstructionOpcode getInstructionOpcode() {
        return new laboflieven.common.AccInstructionOpcode(AccInstructionOpcodeEnum.JumpIfGteStart);
    }

}
