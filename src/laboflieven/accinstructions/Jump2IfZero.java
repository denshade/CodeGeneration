package laboflieven.accinstructions;

import laboflieven.statements.Register;

public class Jump2IfZero  extends AccRegisterInstruction
{
    public Integer execute(Register left, Register right, int ip)
    {
        if (left.value == 0)
        {
            return ip + 2;
        }
        return null;
    }

    @Override
    public String toString() {
        return " Jump if left = 0 goto this + 2";
    }
    @Override
    public Object getInstructionOpcode() {
        return AccInstructionSet.Jump2IfZero;
    }
}
