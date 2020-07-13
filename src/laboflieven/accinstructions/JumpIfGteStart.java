package laboflieven.accinstructions;

import laboflieven.statements.Register;

public class JumpIfGteStart  extends AccRegisterInstruction
{


    public Integer execute(Register left, Register right, int ip)
    {
        if (left.value >= right.value)
        {
            return 0;
        }
        return null;
    }

    @Override
    public String toString() {
        return " Jump to start if L >= R ";
    }
    @Override
    public Object getInstructionOpcode() {
        return AccInstructionSet.JumpIfGteStart;
    }

}
