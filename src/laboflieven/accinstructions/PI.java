package laboflieven.accinstructions;

import laboflieven.common.InstructionOpcode;
import laboflieven.registers.Register;

/**
 * Created by lveeckha on 31/05/2015.
 */
public class PI extends NoRegisterInstruction {

    public Integer execute(Register left, Register right, int ip)
    {
        left.value = Math.PI;
        return null;
    }

    public String toString()
    {
        return  "left = PI";
    }
    @Override
    public InstructionOpcode getInstructionOpcode() {
        return new laboflieven.common.AccInstructionOpcode(AccInstructionOpcodeEnum.PI);
    }

}
