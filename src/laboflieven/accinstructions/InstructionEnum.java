package laboflieven.accinstructions;

/**
 * Created by lveeckha on 4/06/2015.
 */
public enum InstructionEnum
{
    Add, Div, Invert, Mul, Sqrt, Sub, Sin, Cos, Mod, Nand, Log, AccLeftPull, AccLeftPush, AccRightPush, AccRightPull;

    public boolean isSingleRegister() {
        return this.equals(AccLeftPull) || this.equals(AccLeftPush) || this.equals(AccRightPull) || this.equals(AccRightPush);
    }


}
