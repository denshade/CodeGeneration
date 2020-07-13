package laboflieven.accinstructions;

import laboflieven.statements.Register;

/**
 * Created by lveeckha on 31/05/2015.
 */
public class Swap extends AccRegisterInstruction
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
    public Object getInstructionOpcode() {
        return AccInstructionSet.Swap;
    }
}
