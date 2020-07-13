package laboflieven.common;


import laboflieven.statements.InstructionSet;

public class RegularInstructionOpcode implements InstructionOpcode
{
    private final InstructionSet enumer;

    public RegularInstructionOpcode(InstructionSet enumer) {
        this.enumer = enumer;
    }

    public InstructionSet getEnumer() {
        return enumer;
    }
}
