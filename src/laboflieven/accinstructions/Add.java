package laboflieven.accinstructions;

import laboflieven.statements.Register;

/**
 * Created by lveeckha on 31/05/2015.
 */
public class Add extends AccRegisterInstruction
{

    public Integer execute(Register left, Register right)
    {
        left.value = left.value + right.value;
        return null;
    }

    public String toString()
    {
        return  "left = left + right ";    }
}
