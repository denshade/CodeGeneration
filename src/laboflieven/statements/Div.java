package laboflieven.statements;

/**
 * Created by lveeckha on 31/05/2015.
 */
public class Div extends DualRegisterInstruction
{
    public Div(Register source, Register destination) {
        super(source, destination);
    }

    public Integer execute(int current)
    {
        destination.value = destination.value / source.value;
        return null;
    }

    public String toString()
    {
        return destination + " /= " +  source;
    }

    @Override
    public Object getInstructionOpcode() {
        return RegularInstructionOpcode.Div;
    }

}
