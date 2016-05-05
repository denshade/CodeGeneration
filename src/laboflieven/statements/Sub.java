package laboflieven.statements;

/**
 * Created by lveeckha on 31/05/2015.
 */
public class Sub extends DualRegisterInstruction {
    public Sub(Register source, Register destination) {
        super(source, destination);
    }

    public Integer execute()
    {
        destination.value = source.value - destination.value;
        return null;
    }
}
