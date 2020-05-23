package laboflieven.statements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lveeckha on 4/06/2015.
 */
public enum InstructionEnum
{
    Add, Div, Invert, Move, Mul, Sqrt, Sub, Sin, Cos, Mod, Nand, Log, JmpIfZero, JmpIfZeroBegin, JmpIfZeroEnd, PI, Zero, One, JmpIfNotZeroBegin, JmpIfNotZeroEnd, JmpIfRegister2Steps;

    public boolean isDualRegister() {
        return !(this.equals(Invert) || this.equals(Sin) || this.equals(Cos)) && !this.equals(Sqrt) && !this.equals(Log) &&!this.equals(PI) &&!this.equals(Zero) && !this.equals(One)
                && !this.equals(JmpIfZeroBegin) && !this.equals(JmpIfZeroEnd)
                && !this.equals(JmpIfNotZeroBegin) && !this.equals(JmpIfNotZeroEnd) && !this.equals(JmpIfRegister2Steps);
    }

    public static InstructionEnum[] anyExcept(InstructionEnum eq)
    {
        List<InstructionEnum> result = new ArrayList<InstructionEnum>();
        for (InstructionEnum enu : InstructionEnum.values())
        {
            if (!enu.equals(eq)) {
                result.add(enu);
            }
        }
        return result.toArray(new InstructionEnum[0]);
    }

}
