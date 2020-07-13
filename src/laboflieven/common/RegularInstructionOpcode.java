package laboflieven.common;


public class RegularInstructionOpcode implements InstructionOpcode
{
    private final laboflieven.statements.RegularInstructionOpcode enumer;

    public RegularInstructionOpcode(laboflieven.statements.RegularInstructionOpcode enumer) {
        this.enumer = enumer;
    }

    public laboflieven.statements.RegularInstructionOpcode getEnumer() {
        return enumer;
    }
}
