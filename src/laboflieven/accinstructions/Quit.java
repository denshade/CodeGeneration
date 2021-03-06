package laboflieven.accinstructions;

import laboflieven.common.InstructionOpcode;
import laboflieven.statements.Register;

/**
 * Created by lveeckha on 31/05/2015.
 */
public class Quit extends AccRegisterInstruction
{

    public Integer execute(Register left, Register right, int ip)
    {
        return 100000;
    }

    public String toString()
    {
        return  "Quit";
    }
    @Override
    public InstructionOpcode getInstructionOpcode() {
        return new laboflieven.common.AccInstructionOpcode(AccInstructionOpcodeEnum.Quit);
    }
}
