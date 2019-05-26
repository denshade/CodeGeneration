package laboflieven.accinstructions;

import laboflieven.statements.Register;

public class AccLeftPull extends SingleRegisterInstruction
{
    private Register register;

    AccLeftPull(Register register)
    {
        this.register = register;
    }

    public Integer execute(Register left, Register right)
    {
        register.value = left.value;
        return null;
    }

    @Override
    public String toString() {
        return " AccLeft -> " + register.name ;
    }

}
