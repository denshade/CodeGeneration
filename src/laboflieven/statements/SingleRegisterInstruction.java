package laboflieven.statements;

/**
 * Created by lveeckha on 4/06/2015.
 */
public abstract class SingleRegisterInstruction extends Instruction
{
    public Register destination;

    public String toString()
    {
        return this.getClass().getSimpleName() + " " + destination;
    }

}
