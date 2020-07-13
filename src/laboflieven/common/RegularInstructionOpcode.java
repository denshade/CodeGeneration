package laboflieven.common;


import laboflieven.statements.InstructionEnum;

public class RegularInstructionOpcode implements InstructionOpcode
{
    private final InstructionEnum enumer;

    public RegularInstructionOpcode(InstructionEnum enumer) {
        this.enumer = enumer;
    }

    public InstructionEnum getEnumer() {
        return enumer;
    }
}
