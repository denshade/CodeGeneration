package laboflieven.instructions.regular;

import laboflieven.common.InstructionOpcode;
import laboflieven.registers.Register;

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
    public InstructionOpcode getInstructionOpcode() {
        return new laboflieven.common.RegularInstructionOpcode(RegularInstructionOpcodeEnum.Div);
    }

}
