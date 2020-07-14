package laboflieven.accinstructions;

import laboflieven.common.InstructionOpcode;
import laboflieven.statements.Register;

public class Jump2IfGte extends AccRegisterInstruction
{
    public Integer execute(Register left, Register right, int ip)
    {
        if (left.value >= right.value)
        {
            return ip + 2;
        }
        return null;
    }

    @Override
    public String toString() {
        return " Jump if left >= right goto this + 2";
    }
    @Override
    public InstructionOpcode getInstructionOpcode() {
        return new laboflieven.common.AccInstructionOpcode(AccInstructionOpcode.Jump2IfGte);
    }
}
