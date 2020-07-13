package laboflieven.accinstructions;

import laboflieven.common.ArrayOperations;

import java.util.Set;

/**
 * Created by lveeckha on 4/06/2015.
 */
public enum AccInstructionSet
{
    Add, Div, Invert, Mul, Sqrt, Sub, Sin, Cos, Mod, Nand, Log, AccLeftPull, AccLeftPush, AccRightPush, AccRightPull, JumpIfLteStart, JumpIfGteStart/*JumpIfLte, JumpIfGte*/, Jump2IfGte, Jump2IfLte, Jump2IfEq, Jump2IfNeq, Jump2IfZero, Quit, Pow, Swap;
    public boolean isSingleRegister() {
        return this.equals(AccLeftPull) || this.equals(AccLeftPush) || this.equals(AccRightPull) || this.equals(AccRightPush);
    }

    public static AccInstructionSet[] getMinimal() {
        return anyExcept(Set.of(AccRightPull, AccRightPush, JumpIfLteStart, JumpIfGteStart));
    }


    public static AccInstructionSet[] allInstructionsExcept(AccInstructionSet except)
    {
        return anyExcept(Set.of(except));
    }

    public static AccInstructionSet[] anyExcept(Set<AccInstructionSet> eq)
    {
        ArrayOperations<AccInstructionSet> ops = new ArrayOperations<>();
        return ops.anyExcept(eq, AccInstructionSet.values()).toArray(new AccInstructionSet[0]);
    }
}
