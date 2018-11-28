package laboflieven.statements;

/**
 * Created by lveeckha on 4/06/2015.
 */
public enum InstructionEnum
{
    Add, Div, Invert, Move, Mul, Sqrt, Sub, Sin, Cos, Mod, Nand;

    public boolean isDualRegister() {
        return !(this.equals(Invert) || this.equals(Sin) || this.equals(Cos)) && !this.equals(Sqrt);
    }

}
