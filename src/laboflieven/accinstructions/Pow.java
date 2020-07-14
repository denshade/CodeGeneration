package laboflieven.accinstructions;

import laboflieven.common.InstructionOpcode;
import laboflieven.statements.Register;

/**
 */
public class Pow extends AccRegisterInstruction
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
