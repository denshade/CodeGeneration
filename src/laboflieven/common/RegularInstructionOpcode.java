package laboflieven.common;


import laboflieven.statements.RegularInstructionOpcodeEnum;

public class RegularInstructionOpcode implements InstructionOpcode
{
    private final RegularInstructionOpcodeEnum enumer;

    public RegularInstructionOpcode(RegularInstructionOpcodeEnum enumer) {
        this.enumer = enumer;
    }

    public RegularInstructionOpcodeEnum getEnumer() {
        return enumer;
    }

    @Override
    public int getNrRegisters() {
        return 0;
    }
}
