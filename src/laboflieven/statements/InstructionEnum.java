package laboflieven.statements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lveeckha on 4/06/2015.
 */
public enum InstructionEnum
{
    Add, Div, Invert, Move, Mul, Sqrt, Sub, Sin, Cos, Mod, Nand, Log, JmpIfZero, PI, Zero, One;

    public boolean isDualRegister() {
        return !(this.equals(Invert) || this.equals(Sin) || this.equals(Cos)) && !this.equals(Sqrt) && !this.equals(Log) &&!this.equals(PI) &&!this.equals(Zero) && !this.equals(One);
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
