package statements;

/**
 * Created by lveeckha on 31/05/2015.
 */
public class Move extends DualRegisterInstruction
{

    public Move(Register source, Register destination) {
        super(source, destination);
    }

    public void execute()
    {
        destination.value = source.value;
    }
}
