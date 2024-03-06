package laboflieven.instructions.regular;

import laboflieven.common.InstructionOpcode;
import laboflieven.registers.Register;

/**
 * Invert the signum.
 */

public class Invert extends SingleRegisterInstruction
{

    public Invert(Register destination) {
        this.destination = destination;
    }

    public Integer execute(int current) {
        destination.value = destination.value * -1;
        return null;
    }
    @Override
    public InstructionOpcode getInstructionOpcode() {
        return new laboflieven.common.RegularInstructionOpcode(RegularInstructionOpcodeEnum.Invert);
    }

}
