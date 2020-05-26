package laboflieven.accinstructions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Created by lveeckha on 4/06/2015.
 */
public enum InstructionEnum
{
    Add, Div, Invert, Mul, Sqrt, Sub, Sin, Cos, Mod, Nand, Log, AccLeftPull, AccLeftPush, AccRightPush, AccRightPull, JumpIfLteStart, JumpIfGteStart/*JumpIfLte, JumpIfGte*/, Jump2IfGte, Jump2IfLte, Jump2IfEq, Jump2IfNeq, Jump2IfZero, Quit;

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

    public static InstructionEnum[] anyExcept(Set<InstructionEnum> eq)
    {
        List<InstructionEnum> result = new ArrayList<InstructionEnum>();
        for (InstructionEnum enu : InstructionEnum.values())
        {
            if (!eq.contains(enu)) {
                result.add(enu);
            }
        }
        return result.toArray(new InstructionEnum[0]);
    }
}
