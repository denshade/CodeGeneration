package statements;

/**
 * Created by lveeckha on 4/06/2015.
 */
abstract class SingleRegisterInstruction extends Instruction
{
    protected Register destination;

    public String toString()
    {
        return this.getClass().getSimpleName() + " " + destination;
    }

}
