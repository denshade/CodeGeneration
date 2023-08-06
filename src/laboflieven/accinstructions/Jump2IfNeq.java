package laboflieven.accinstructions;

import laboflieven.common.InstructionOpcode;
import laboflieven.registers.Register;

public class Jump2IfNeq  extends NoRegisterInstruction
{
    public Integer execute(Register left, Register right, int ip)
    {
        if (left.value != right.value)
        {
            return ip + 2;
        }
        return null;
    }

    @Override
    public String toString() {
        return "Jump if left >= right goto this + 2";
    }

    @Override
    public InstructionOpcode getInstructionOpcode() {
        return new laboflieven.common.AccInstructionOpcode(AccInstructionOpcodeEnum.Jump2IfNeq);
    }
}
