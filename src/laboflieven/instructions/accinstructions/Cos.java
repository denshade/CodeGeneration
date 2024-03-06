package laboflieven.instructions.accinstructions;

import laboflieven.common.InstructionOpcode;
import laboflieven.registers.Register;

/**
 * Created by Lieven on 23-4-2016.
 */
public class Cos extends NoRegisterInstruction {

    public Integer execute(Register left, Register right, int ip)
    {
        left.value = Math.cos(left.value);
        return null;
    }

    public String toString()
    {
        return  "left = cos(left)";
    }
    @Override
    public InstructionOpcode getInstructionOpcode() {
        return new laboflieven.common.AccInstructionOpcode(AccInstructionOpcodeEnum.Cos);
    }
}
