package laboflieven.accinstructions;

import laboflieven.common.InstructionOpcode;
import laboflieven.statements.Register;

public class AccLeftPush extends SingleRegisterInstruction
{
    public AccLeftPush(Register register)
    {
        this.register = register;
    }

    public Integer execute(Register left, Register right, int ip)
    {
        left.value = register.value;
        return null;
    }

    @Override
    public String toString() {
        return " left = " +  register.name;
    }
    @Override
    public InstructionOpcode getInstructionOpcode() {
        return new laboflieven.common.AccInstructionOpcode(AccInstructionOpcode.AccLeftPush);
    }
}
