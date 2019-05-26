package laboflieven.accinstructions;

import laboflieven.statements.Register;

public class AccRightPush extends SingleRegisterInstruction
{
    private Register register;

    AccRightPush(Register register)
    {
        this.register = register;
    }

    public Integer execute(Register left, Register right)
    {
        right.value = register.value;
        return null;
    }

    @Override
    public String toString() {
        return register.name + " -> AccRight ";
    }

}
