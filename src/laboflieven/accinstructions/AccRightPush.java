package laboflieven.accinstructions;

import laboflieven.common.InstructionOpcode;
import laboflieven.statements.Register;

public class AccRightPush extends SingleRegisterInstruction
{
    public AccRightPush(Register register)
    {
        this.register = register;
    }

    public Integer execute(Register left, Register right, int ip)
    {
        right.value = register.value;
        return null;
    }

    @Override
    public String toString() {
        return " right = " + register.name;
    }


    @Override
    public InstructionOpcode getInstructionOpcode() {
        return new laboflieven.common.AccInstructionOpcode(AccInstructionOpcodeEnum.AccRightPush);
    }
}
