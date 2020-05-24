package laboflieven.accinstructions;

import laboflieven.statements.Register;

public abstract class AccRegisterInstruction {


    abstract public Integer execute(Register left, Register right, int ip);

    abstract public String toString();
}
