package laboflieven.accinstructions;

import laboflieven.common.ArrayOperations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Created by lveeckha on 4/06/2015.
 */
public enum InstructionEnum
{
    Add, Div, Invert, Mul, Sqrt, Sub, Sin, Cos, Mod, Nand, Log, AccLeftPull, AccLeftPush, AccRightPush, AccRightPull, JumpIfLteStart, JumpIfGteStart/*JumpIfLte, JumpIfGte*/, Jump2IfGte, Jump2IfLte, Jump2IfEq, Jump2IfNeq, Jump2IfZero, Quit, Pow;

    public boolean isSingleRegister() {
        return this.equals(AccLeftPull) || this.equals(AccLeftPush) || this.equals(AccRightPull) || this.equals(AccRightPush);
    }


    public static InstructionEnum[] allInstructionsExcept(InstructionEnum except)
    {
        return anyExcept(Set.of(except));
    }

    public static InstructionEnum[] anyExcept(Set<InstructionEnum> eq)
    {
        return (InstructionEnum[]) ArrayOperations.anyExcept(eq, InstructionEnum.values());
    }
}
