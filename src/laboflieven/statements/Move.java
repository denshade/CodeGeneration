package laboflieven.statements;

/**
 * Created by lveeckha on 31/05/2015.
 */
public class Move extends DualRegisterInstruction
{

    public Move(Register source, Register destination) {
        super(source, destination);
    }

    public Integer execute(int current)
    {
        destination.value = source.value;
        return null;
    }

    @Override
    public Object getInstructionOpcode() {
        return RegularInstructionOpcode.Move;
    }

}
