package laboflieven.accinstructions;

import laboflieven.common.InstructionOpcode;
import laboflieven.statements.Register;

public class LoadIntoRightAcc extends SingleRegisterInstruction
{
    public LoadIntoRightAcc(Register register)
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
