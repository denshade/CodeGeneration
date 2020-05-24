package laboflieven.accinstructions;

import laboflieven.statements.Register;

/**
 * Created by lveeckha on 31/05/2015.
 */
public class Mul extends AccRegisterInstruction
{
    public Integer execute(Register left, Register right, int ip)
    {
        left.value = left.value * right.value;
        return null;
    }

    public String toString()
    {
        return  "left = left * right";
    }
}
