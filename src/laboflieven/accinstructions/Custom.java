package laboflieven.accinstructions;

import laboflieven.common.InstructionOpcode;
import laboflieven.statements.Register;

public class Custom extends NoRegisterInstruction
{

    public Custom() {

    }

    public Integer execute(Register left, Register right, int ip)
    {
        left.value = left.value * 3 + 1;
        return null;
    }

    public String toString()
    {
        return  "left = 3n+1";
    }
    @Override
    public InstructionOpcode getInstructionOpcode() {
        return new laboflieven.common.AccInstructionOpcode(AccInstructionOpcodeEnum.Div);
    }
}
