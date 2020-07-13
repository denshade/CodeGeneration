package laboflieven.common;

import laboflieven.accinstructions.AccInstructionSet;

public class AccInstructionOpcode implements InstructionOpcode
{
    private final AccInstructionSet enumer;

    public AccInstructionOpcode(AccInstructionSet enumer) {
        this.enumer = enumer;
    }

    public AccInstructionSet getEnumer() {
        return enumer;
    }
}
