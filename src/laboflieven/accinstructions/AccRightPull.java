package laboflieven.accinstructions;

import laboflieven.statements.Register;

public class AccRightPull extends SingleRegisterInstruction
{
    private Register register;

    AccRightPull(Register register)
    {
        this.register = register;
    }

    public Integer execute(Register left, Register right, int ip)
    {
        register.value = right.value;
        return null;
    }

    @Override
    public String toString() {
        return  register.name + " = right";
    }

}
