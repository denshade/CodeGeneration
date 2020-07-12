package laboflieven.accinstructions;

import laboflieven.statements.Register;

public class JumpIfGte  extends AccRegisterInstruction
{

    private final Register register;

    JumpIfGte(Register register)
    {
        this.register = register;
    }

    public Integer execute(Register left, Register right, int ip)
    {
        if (left.value >= right.value)
        {
            return (int)register.value;
        }
        return null;
    }

    @Override
    public String toString() {
        return " Jump if >= " + register.name;
    }

}
