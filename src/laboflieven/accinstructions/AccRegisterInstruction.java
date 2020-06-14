package laboflieven.accinstructions;

import laboflieven.InstructionMark;
import laboflieven.statements.Register;

public abstract class AccRegisterInstruction implements InstructionMark {


    abstract public Integer execute(Register left, Register right, int ip);

    abstract public String toString();
}
