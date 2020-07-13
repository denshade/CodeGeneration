package laboflieven.accinstructions;

import laboflieven.common.ArrayOperations;

import java.util.Set;

/**
 * Created by lveeckha on 4/06/2015.
 */
public enum AccInstructionOpcode
{
    Add, Div, Invert, Mul, Sqrt, Sub, Sin, Cos, Mod, Nand, Log, AccLeftPull, AccLeftPush, AccRightPush, AccRightPull, JumpIfLteStart, JumpIfGteStart/*JumpIfLte, JumpIfGte*/, Jump2IfGte, Jump2IfLte, Jump2IfEq, Jump2IfNeq, Jump2IfZero, Quit, Pow, Swap;
    public boolean isSingleRegister() {
        return this.equals(AccLeftPull) || this.equals(AccLeftPush) || this.equals(AccRightPull) || this.equals(AccRightPush);
    }

    public static AccInstructionOpcode[] getMinimal() {
        return anyExcept(Set.of(AccRightPull, AccRightPush, JumpIfLteStart, JumpIfGteStart));
    }


    public static AccInstructionOpcode[] allInstructionsExcept(AccInstructionOpcode except)
    {
        return anyExcept(Set.of(except));
    }

    public static AccInstructionOpcode[] anyExcept(Set<AccInstructionOpcode> eq)
    {
        ArrayOperations<AccInstructionOpcode> ops = new ArrayOperations<>();
        return ops.anyExcept(eq, AccInstructionOpcode.values()).toArray(new AccInstructionOpcode[0]);
    }
}
