package laboflieven.accinstructions;

import laboflieven.common.InstructionOpcode;
import laboflieven.statements.Register;

public class LoadAccLeftIntoRegister extends SingleRegisterInstruction
{

    public LoadAccLeftIntoRegister(Register register)
    {
        this.register = register;
    }

    public Integer execute(Register left, Register right, int ip)
    {
        register.value = left.value;
        return null;
    }

    @Override
    public String toString() {
        return register.name +" = left" ;
    }

    @Override
    public InstructionOpcode getInstructionOpcode() {
        return new laboflieven.common.AccInstructionOpcode(AccInstructionOpcodeEnum.AccLeftPull);
    }
}
