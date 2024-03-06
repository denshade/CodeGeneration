package laboflieven.instructions.accinstructions;

import laboflieven.common.InstructionOpcode;
import laboflieven.registers.Register;

public class LoadIntoLeftAcc extends SingleRegisterInstruction
{
    public LoadIntoLeftAcc(Register register)
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
        return new laboflieven.common.AccInstructionOpcode(AccInstructionOpcodeEnum.LoadIntoLeftAcc);
    }
}
