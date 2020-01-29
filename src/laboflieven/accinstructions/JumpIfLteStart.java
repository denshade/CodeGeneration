package laboflieven.accinstructions;

import laboflieven.statements.Register;

public class JumpIfLteStart extends SingleRegisterInstruction
{


    public Integer execute(Register left, Register right)
    {
        if (left.value <= right.value)
        {
            return 0;
        }
        return null;
    }

    @Override
    public String toString() {
        return " if left <=  R then goto 0 ";
    }

}
