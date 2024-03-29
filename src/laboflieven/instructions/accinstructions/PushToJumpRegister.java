package laboflieven.instructions.accinstructions;

import laboflieven.common.InstructionOpcode;
import laboflieven.registers.Register;

public class PushToJumpRegister implements JumpInstruction
{
    @Override
    public InstructionOpcode getInstructionOpcode() {
        return null;
    }

    @Override
    public Integer execute(Register left, Register right, Register jumpRegister, int ip) {
        jumpRegister.value = left.value;
        return null;
    }

    @Override
    public String toString() {
        return "PushLeftToJumpRegister";
    }
}
