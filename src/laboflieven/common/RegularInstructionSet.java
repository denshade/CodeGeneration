package laboflieven.common;


import laboflieven.statements.InstructionEnum;

public class RegularInstructionSet implements InstructionSet
{
    private final InstructionEnum enumer;

    public RegularInstructionSet(InstructionEnum enumer) {
        this.enumer = enumer;
    }

    public InstructionEnum getEnumer() {
        return enumer;
    }
}
