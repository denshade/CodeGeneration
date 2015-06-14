package laboflieven.statements;

/**
 * Created by lveeckha on 4/06/2015.
 */
public enum InstructionEnum
{
    Add, Div, Invert, Move, Mul, Sqrt, Sub;

    public boolean isDualRegister()
    {
        if (this.equals(Invert)) return false;
        if (this.equals(Sqrt)) return false;
        return true;
    }
}
