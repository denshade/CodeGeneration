package laboflieven.statements;

import laboflieven.common.RegularInstructionOpcode;

/**
 * Created by lveeckha on 31/05/2015.
 */
public class Add extends DualRegisterInstruction
{

    public Add(Register source, Register destination) {
        super(source, destination);
    }

    public Integer execute(int current)
    {
        destination.value = source.value + destination.value;
        return null;
    }

    public String toString()
    {
        return destination + " += " +  source;
    }

    @Override
    public Object getInstructionOpcode() {
        return InstructionSet.Add;
    }
}
