package laboflieven.statements;

/**
 * Created by lveeckha on 31/05/2015.
 */
public class Mod extends DualRegisterInstruction
{
    public Mod(Register source, Register destination) {
        super(source, destination);
    }

    public Integer execute(int current)
    {
        destination.value = destination.value % source.value;
        return null;
    }
    public String toString()
    {
        return destination.name + " = " + destination.name + " % " + source.name;
    }
}
