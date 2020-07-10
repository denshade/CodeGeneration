package laboflieven.common;

import laboflieven.accinstructions.InstructionEnum;

public class AccEnumWrapper implements EnumWrapper
{
    private final InstructionEnum enumer;

    public AccEnumWrapper(InstructionEnum enumer) {
        this.enumer = enumer;
    }

    public InstructionEnum getEnumer() {
        return enumer;
    }
}
