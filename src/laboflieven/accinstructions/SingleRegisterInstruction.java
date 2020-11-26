package laboflieven.accinstructions;

import laboflieven.statements.Register;

/**
 * Created by lveeckha on 4/06/2015.
 */
public abstract class SingleRegisterInstruction extends AccRegisterInstruction
{
    public Register getRegister() {
        return register;
    }

    protected Register register;

    public void setRegister(Register reg) {
        register = reg;
    }
}
