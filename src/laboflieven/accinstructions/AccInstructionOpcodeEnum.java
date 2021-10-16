package laboflieven.accinstructions;

import laboflieven.common.ArrayOperations;

import java.util.Set;

/**
 * Created by lveeckha on 4/06/2015.
 */
public enum AccInstructionOpcodeEnum
{

    Add(0), Div(0), Invert(0), Inc(0), Mul(0), Sqrt(0), Sub(0), Sin(0), Cos(0), Mod(0), Nand(0), Log(0), LoadAccLeftIntoRegister(1), LoadIntoLeftAcc(1), LoadIntoRightAcc(1), LoadAccRightIntoRegister(1),
    JumpIfLteStart(0), JumpIfGteStart(0)/*JumpIfLte, JumpIfGte*/, Jump2IfGte(0), Jump2IfLte(0), Jump2IfEq(0), Jump2IfNeq(0),
    Jump2IfZero(0), Quit(0), Pow(0), Swap(0), PI(0), E(0), Custom(0);

    private final int nrRegisters;

    AccInstructionOpcodeEnum(int nrRegisters)
    {
        this.nrRegisters = nrRegisters;
    }

    public int getNrRegisters() {
        return nrRegisters;
    }

    public boolean isSingleRegister() {
        return this.nrRegisters == 1;
    }
}
