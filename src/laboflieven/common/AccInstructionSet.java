package laboflieven.common;

import laboflieven.accinstructions.InstructionEnum;

public class AccInstructionSet implements InstructionSet
{
    private final InstructionEnum enumer;

    public AccInstructionSet(InstructionEnum enumer) {
        this.enumer = enumer;
    }

    public InstructionEnum getEnumer() {
        return enumer;
    }
}
