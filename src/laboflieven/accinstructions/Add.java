package laboflieven.accinstructions;

import laboflieven.common.InstructionOpcode;
import laboflieven.statements.Register;

/**
 * Created by lveeckha on 31/05/2015.
 */
public class Add extends NoRegisterInstruction
{

    public Integer execute(Register left, Register right, int ip)
    {
        left.value = left.value + right.value;
        return null;
    }

    public String toString()
    {
        return  "left = left + right";
    }
    @Override
    public InstructionOpcode getInstructionOpcode() {
        return new laboflieven.common.AccInstructionOpcode(AccInstructionOpcodeEnum.Add);
    }
}
