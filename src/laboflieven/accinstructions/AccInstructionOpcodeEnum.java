package laboflieven.accinstructions;

import laboflieven.common.ArrayOperations;

import java.util.Set;

/**
 * Created by lveeckha on 4/06/2015.
 */
public enum AccInstructionOpcodeEnum
{
    Add, Div, Invert, Mul, Sqrt, Sub, Sin, Cos, Mod, Nand, Log, AccLeftPull, AccLeftPush, AccRightPush, AccRightPull, JumpIfLteStart, JumpIfGteStart/*JumpIfLte, JumpIfGte*/, Jump2IfGte, Jump2IfLte, Jump2IfEq, Jump2IfNeq, Jump2IfZero, Quit, Pow, Swap;
    public boolean isSingleRegister() {
        return this.equals(AccLeftPull) || this.equals(AccLeftPush) || this.equals(AccRightPull) || this.equals(AccRightPush);
    }

    public static AccInstructionOpcodeEnum[] getMinimal() {
        return anyExcept(Set.of(AccRightPull, AccRightPush, JumpIfLteStart, JumpIfGteStart));
    }


    public static AccInstructionOpcodeEnum[] allInstructionsExcept(AccInstructionOpcodeEnum except)
    {
        return anyExcept(Set.of(except));
    }

    public static AccInstructionOpcodeEnum[] anyExcept(Set<AccInstructionOpcodeEnum> eq)
    {
        ArrayOperations<AccInstructionOpcodeEnum> ops = new ArrayOperations<>();
        return ops.anyExcept(eq, AccInstructionOpcodeEnum.values()).toArray(new AccInstructionOpcodeEnum[0]);
    }
}
