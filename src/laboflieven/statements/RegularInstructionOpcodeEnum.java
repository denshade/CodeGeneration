package laboflieven.statements;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by lveeckha on 4/06/2015.
 */
public enum RegularInstructionOpcodeEnum
{
    Add, Div, Invert, Move, Mul, Sqrt, Sub, Sin, Cos, Mod, Nand, Log, JmpIfZero, JmpIfZeroBegin, JmpIfZeroEnd, PI, Zero, One, JmpIfNotZeroBegin, JmpIfNotZeroEnd, JmpIfRegister2Steps;

    public boolean isDualRegister() {
        return !(this.equals(Invert) || this.equals(Sin) || this.equals(Cos)) && !this.equals(Sqrt) && !this.equals(Log) &&!this.equals(PI) &&!this.equals(Zero) && !this.equals(One)
                && !this.equals(JmpIfZeroBegin) && !this.equals(JmpIfZeroEnd)
                && !this.equals(JmpIfNotZeroBegin) && !this.equals(JmpIfNotZeroEnd) && !this.equals(JmpIfRegister2Steps);
    }

    public static RegularInstructionOpcodeEnum[] anyExcept(RegularInstructionOpcodeEnum eq)
    {
        List<RegularInstructionOpcodeEnum> result = new ArrayList<RegularInstructionOpcodeEnum>();
        for (RegularInstructionOpcodeEnum enu : RegularInstructionOpcodeEnum.values())
        {
            if (!enu.equals(eq)) {
                result.add(enu);
            }
        }
        return result.toArray(new RegularInstructionOpcodeEnum[0]);
    }

    public static RegularInstructionOpcodeEnum[] anyExcept(Set<RegularInstructionOpcodeEnum> eq)
    {
        List<RegularInstructionOpcodeEnum> result = new ArrayList<RegularInstructionOpcodeEnum>();
        for (RegularInstructionOpcodeEnum enu : RegularInstructionOpcodeEnum.values())
        {
            if (!eq.contains(enu)) {
                result.add(enu);
            }
        }
        return result.toArray(new RegularInstructionOpcodeEnum[0]);
    }

}
