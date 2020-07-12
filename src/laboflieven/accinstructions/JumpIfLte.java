package laboflieven.accinstructions;

import laboflieven.statements.Register;

public class JumpIfLte extends AccRegisterInstruction
{

    private final Register register;

    JumpIfLte(Register register)
    {
        this.register = register;
    }

    public Integer execute(Register left, Register right, int ip)
    {
        if (left.value <= right.value)
        {
            return (int)register.value;
        }
        return null;
    }

    @Override
    public String toString() {
        return " Jump if lte to " + register.name;
    }

}
