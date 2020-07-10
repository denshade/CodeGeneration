package laboflieven.common;


import laboflieven.statements.InstructionEnum;

public class RegularEnumWrapper implements EnumWrapper
{
    private final InstructionEnum enumer;

    public RegularEnumWrapper(InstructionEnum enumer) {
        this.enumer = enumer;
    }

    public InstructionEnum getEnumer() {
        return enumer;
    }
}
