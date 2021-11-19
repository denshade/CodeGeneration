package laboflieven.accinstructions;

import laboflieven.common.InstructionOpcode;
import laboflieven.statements.Register;

public class Dec extends AccRegisterInstruction {

    public Integer execute(Register left, Register right, int ip)
    {
        left.value = left.value - 1;
        return null;
    }

    public String toString()
    {
        return  "left--";
    }

    @Override
    public InstructionOpcode getInstructionOpcode() {
        return new laboflieven.common.AccInstructionOpcode(AccInstructionOpcodeEnum.Dec);
    }

}