package laboflieven.statements;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by lveeckha on 4/06/2015.
 */
public enum RegularInstructionOpcodeEnum
{
    Add(2), Div(2), Invert(1), Move(2), Mul(2), Sqrt(1), Sub(2),
    Sin(1), Cos(1), Mod(2), Nand(2), Log(1), JmpIfZero(2),
    JmpIfZeroBegin(1), JmpIfZeroEnd(1), PI(0), Zero(0), One(0),
    JmpIfNotZeroBegin(1), JmpIfNotZeroEnd(1), JmpIfRegister2Steps(1);

    private final int nrRegisters;

    private RegularInstructionOpcodeEnum(int nrRegisters)
    {
        this.nrRegisters = nrRegisters;
    }

    public boolean isDualRegister() {
        return this.nrRegisters == 2;
    }

    public int getNrRegisters() {
        return nrRegisters;
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
