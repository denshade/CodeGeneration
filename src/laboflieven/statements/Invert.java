package laboflieven.statements;

import laboflieven.common.InstructionOpcode;

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
        return new laboflieven.common.RegularInstructionOpcode(RegularInstructionOpcode.Invert);
    }

}
