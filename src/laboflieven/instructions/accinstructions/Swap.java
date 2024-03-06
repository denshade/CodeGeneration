package laboflieven.instructions.accinstructions;

import laboflieven.common.InstructionOpcode;
import laboflieven.registers.Register;

/**
 * Created by lveeckha on 31/05/2015.
 */
public class Swap extends NoRegisterInstruction
{

    public Integer execute(Register left, Register right, int ip)
    {
        double swap = left.value;
        left.value = right.value;
        right.value = swap;
        return null;
    }

    public String toString()
    {
        return  "swap = left, left = right, right = swap";
    }

    @Override
    public InstructionOpcode getInstructionOpcode() {
        return new laboflieven.common.AccInstructionOpcode(AccInstructionOpcodeEnum.Swap);
    }
}
