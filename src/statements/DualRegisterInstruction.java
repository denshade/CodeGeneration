package statements;

/**
 * Created by lveeckha on 31/05/2015.
 */
public abstract class DualRegisterInstruction extends Instruction {

    public Register source;
    public Register destination;

    public DualRegisterInstruction(Register source, Register destination)
    {
        this.source = source;
        this.destination = destination;
    }

    abstract public void execute();

    public String toString()
    {
        return this.getClass().getSimpleName() + " " + source + " " + destination ;
    }

}
