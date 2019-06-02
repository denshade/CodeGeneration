package laboflieven.accinstructions;

import laboflieven.statements.Register;

public class AccLeftPush extends SingleRegisterInstruction
{
    private Register register;

    public AccLeftPush(Register register)
    {
        this.register = register;
    }

    public Integer execute(Register left, Register right)
    {
        left.value = register.value;
        return null;
    }

    @Override
    public String toString() {
        return register.name + " -> AccLeft ";
    }

}
