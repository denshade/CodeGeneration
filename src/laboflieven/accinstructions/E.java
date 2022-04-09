package laboflieven.accinstructions;

import laboflieven.common.InstructionOpcode;
import laboflieven.statements.Register;

/**
 * Created by lveeckha on 31/05/2015.
 */
public class E extends NoRegisterInstruction {

    public Integer execute(Register left, Register right, int ip)
    {
        left.value = Math.E;
        return null;
    }

    public String toString()
    {
        return  "left = E";
    }
    @Override
    public InstructionOpcode getInstructionOpcode() {
        return new laboflieven.common.AccInstructionOpcode(AccInstructionOpcodeEnum.E);
    }

}
