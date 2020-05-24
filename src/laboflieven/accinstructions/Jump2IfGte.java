package laboflieven.accinstructions;

import laboflieven.statements.Register;

public class Jump2IfGte extends SingleRegisterInstruction
{
    public Integer execute(Register left, Register right, int ip)
    {
        if (left.value >= right.value)
        {
            return ip + 2;
        }
        return null;
    }

    @Override
    public String toString() {
        return " Jump if left >= right goto this + 2";
    }

}
