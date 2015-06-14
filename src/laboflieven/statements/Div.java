package laboflieven.statements;

/**
 * Created by lveeckha on 31/05/2015.
 */
public class Div extends DualRegisterInstruction
{
    public Div(Register source, Register destination) {
        super(source, destination);
    }

    public void execute()
    {
        destination.value = destination.value / source.value;
    }
}
