package laboflieven.accinstructions;

import laboflieven.common.InstructionOpcode;
import laboflieven.registers.Register;

public class Jump2IfZero implements JumpInstruction
{
    public Integer execute(Register left, Register right, Register jump, int ip)
    {
        if (left.value == 0)
        {
            return ip + (int)jump.value;
        }
        return null;
    }

    @Override
    public String toString() {
        return "Jump if left = 0 goto this + jump";
    }
    @Override
    public InstructionOpcode getInstructionOpcode() {
        return new laboflieven.common.AccInstructionOpcode(AccInstructionOpcodeEnum.Jump2IfZero);
    }
}
