package laboflieven.instructions.accinstructions;

import laboflieven.common.InstructionOpcode;
import laboflieven.registers.Register;

/**
 */
public class Pow extends NoRegisterInstruction
{

    public Integer execute(Register left, Register right, int ip)
    {
        left.value = Math.pow(left.value, right.value);
        return null;
    }

    public String toString()
    {
        return  "left = left ^ right";    }
    @Override
    public InstructionOpcode getInstructionOpcode() {
        return new laboflieven.common.AccInstructionOpcode(AccInstructionOpcodeEnum.Pow);
    }
}
