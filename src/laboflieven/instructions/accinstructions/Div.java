package laboflieven.instructions.accinstructions;

import laboflieven.common.InstructionOpcode;
import laboflieven.registers.Register;

/**
 * Created by lveeckha on 31/05/2015.
 */
public class Div extends NoRegisterInstruction
{

    public Integer execute(Register left, Register right, int ip)
    {
        left.value = left.value / right.value;
        return null;
    }

    public String toString()
    {
        return  "left = left / right";
    }
    @Override
    public InstructionOpcode getInstructionOpcode() {
        return new laboflieven.common.AccInstructionOpcode(AccInstructionOpcodeEnum.Div);
    }
}
