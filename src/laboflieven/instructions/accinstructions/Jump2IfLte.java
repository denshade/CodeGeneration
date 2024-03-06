package laboflieven.instructions.accinstructions;

import laboflieven.common.InstructionOpcode;
import laboflieven.registers.Register;

public class Jump2IfLte  implements JumpInstruction
{
    public Integer execute(Register left, Register right, Register jump, int ip)
    {
        if (left.value <= right.value)
        {
            return ip + (int)jump.value;
        }
        return null;
    }

    @Override
    public String toString() {
        return "Jump if left <= right goto this + jump";
    }
    @Override
    public InstructionOpcode getInstructionOpcode() {
        return new laboflieven.common.AccInstructionOpcode(AccInstructionOpcodeEnum.Jump2IfLte);
    }
}
