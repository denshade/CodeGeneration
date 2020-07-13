package laboflieven.accinstructions;

import laboflieven.statements.Register;

/**
 * Created by lveeckha on 31/05/2015.
 */
public class Sqrt extends AccRegisterInstruction {

    public Integer execute(Register left, Register right, int ip)
    {
        left.value = Math.sqrt(left.value);
        return null;
    }

    public String toString()
    {
        return  "left = sqrt(left)";    }
    @Override
    public Object getInstructionOpcode() {
        return AccInstructionSet.Sqrt;
    }
}
