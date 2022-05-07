package laboflieven.accinstructions;

import laboflieven.InstructionMark;
import laboflieven.statements.Register;
import laboflieven.statements.VectorRegister;

public abstract class AccRegisterInstruction implements InstructionMark {


    abstract public Integer execute(Register left, Register right, int ip);
    public Integer execute(Register left, Register right, VectorRegister leftVector, VectorRegister rightVector, int ip)
    {
        return execute(left, right, ip);
    }

    abstract public String toString();

    @Override
    public boolean equals(Object obj) {
        return toString().equals(obj.toString());
    }
}
