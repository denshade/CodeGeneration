package laboflieven.accinstructions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by lveeckha on 4/06/2015.
 */
public enum InstructionEnum
{
    Add, Div, Invert, Mul, Sqrt, Sub, Sin, Cos, Mod, Nand, Log, AccLeftPull, AccLeftPush, AccRightPush, AccRightPull, JumpIfLteStart, JumpIfGteStart/*JumpIfLte, JumpIfGte*/, Jump2IfGte;

    public boolean isSingleRegister() {
        return this.equals(AccLeftPull) || this.equals(AccLeftPush) || this.equals(AccRightPull) || this.equals(AccRightPush);
    }


    public static InstructionEnum[] allInstructionsExcept(InstructionEnum except)
    {
        laboflieven.accinstructions.InstructionEnum[] enums = new InstructionEnum[InstructionEnum.values().length - 1];
        List enumList = new ArrayList<>(Arrays.asList(laboflieven.accinstructions.InstructionEnum.values()));
        enumList.remove(except);
        enumList.toArray(enums);
        return enums;
    }
}
