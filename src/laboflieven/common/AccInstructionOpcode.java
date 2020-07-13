package laboflieven.common;

public class AccInstructionOpcode implements InstructionOpcode
{
    private final laboflieven.accinstructions.AccInstructionOpcode enumer;

    public AccInstructionOpcode(laboflieven.accinstructions.AccInstructionOpcode enumer) {
        this.enumer = enumer;
    }

    public laboflieven.accinstructions.AccInstructionOpcode getEnumer() {
        return enumer;
    }
}
