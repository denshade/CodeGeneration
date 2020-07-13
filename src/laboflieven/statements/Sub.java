package laboflieven.statements;

/**
 * Created by lveeckha on 31/05/2015.
 */
public class Sub extends DualRegisterInstruction {
    public Sub(Register source, Register destination) {
        super(source, destination);
    }

    public Integer execute(int current)
    {
        destination.value = destination.value - source.value;
        return null;
    }

    public String toString()
    {
        return destination.name + " -= " + source.name;
    }
    @Override
    public Object getInstructionOpcode() {
        return RegularInstructionOpcode.Sub;
    }

}
