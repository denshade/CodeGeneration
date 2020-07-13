package laboflieven.common;

import laboflieven.accinstructions.InstructionEnum;

public class AccInstructionOpcode implements InstructionOpcode
{
    private final InstructionEnum enumer;

    public AccInstructionOpcode(InstructionEnum enumer) {
        this.enumer = enumer;
    }

    public InstructionEnum getEnumer() {
        return enumer;
    }
}
