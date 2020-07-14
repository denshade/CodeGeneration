package laboflieven.accinstructions;

import laboflieven.common.InstructionOpcode;
import laboflieven.statements.Register;

public class Nand extends AccRegisterInstruction {

    public Integer execute(Register left, Register right, int ip)
    {
        boolean sourceB = left.value < 0.0000001 ? false: true;
        boolean destinationB = right.value < 0.0000001 ? false: true;
        boolean evaluation = !(sourceB && destinationB);
        left.value = evaluation?1.0:0.0;
        return null;
    }

    public String toString()
    {
        return  "left = nand(left, right)";
    }
    @Override
    public InstructionOpcode getInstructionOpcode() {
        return new laboflieven.common.AccInstructionOpcode(AccInstructionOpcode.Nand);
    }
}
