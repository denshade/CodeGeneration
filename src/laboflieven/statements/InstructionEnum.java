package laboflieven.statements;

/**
 * Created by lveeckha on 4/06/2015.
 */
public enum InstructionEnum
{
    Add, Div, Invert, Move, Mul, Sqrt, Sub, Sin, Cos;

    public boolean isDualRegister()
    {
        if (this.equals(Invert) || this.equals(Sin) || this.equals(Cos)) return false;
        return !this.equals(Sqrt);
    }
}
