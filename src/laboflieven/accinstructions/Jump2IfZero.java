package laboflieven.accinstructions;

import laboflieven.common.InstructionOpcode;
import laboflieven.statements.Register;

public class Jump2IfZero  extends NoRegisterInstruction
{
    public Integer execute(Register left, Register right, int ip)
    {
        if (left.value == 0)
        {
            return ip + 2;
        }
        return null;
    }

    @Override
    public String toString() {
        return "Jump if left = 0 goto this + 2";
    }
    @Override
    public InstructionOpcode getInstructionOpcode() {
        return new laboflieven.common.AccInstructionOpcode(AccInstructionOpcodeEnum.Jump2IfZero);
    }
}
