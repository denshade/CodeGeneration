package laboflieven.accinstructions;

import laboflieven.statements.Register;

/**
 * Invert the signum.
 */

public class Invert extends AccRegisterInstruction
{

    public Invert() {
            }

    public Integer execute(Register left, Register right)
    {
        left.value = left.value * -1;
        return null;
    }

    public String toString()
    {
        return  "left = -left";
    }
}
