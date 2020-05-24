package laboflieven.accinstructions;

import laboflieven.statements.Register;

public class AccLeftPull extends SingleRegisterInstruction
{
    private Register register;

    public AccLeftPull(Register register)
    {
        this.register = register;
    }

    public Integer execute(Register left, Register right, int ip)
    {
        register.value = left.value;
        return null;
    }

    @Override
    public String toString() {
        return register.name +" = left" ;
    }

}
