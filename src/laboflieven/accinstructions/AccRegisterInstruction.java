package laboflieven.accinstructions;

import laboflieven.InstructionMark;
import laboflieven.registers.Register;
import laboflieven.statements.VectorRegister;

public abstract class AccRegisterInstruction implements InstructionMark {


    public Integer execute(Register left, Register right, Register jumpRegister, int ip) {
        return execute(left, right, ip);
    }

    public Integer execute(Register left, Register right, VectorRegister leftVector, VectorRegister rightVector, int ip)
    {
        return execute(left, right, ip);
    }

    public Integer execute(Register left, Register right, int ip) {
        return execute(left, right, null, ip);
    }

    abstract public String toString();

    @Override
    public boolean equals(Object obj) {
        return toString().equals(obj.toString());
    }
}
