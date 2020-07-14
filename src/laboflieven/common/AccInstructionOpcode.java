package laboflieven.common;

import laboflieven.accinstructions.AccInstructionOpcodeEnum;

public class AccInstructionOpcode implements InstructionOpcode
{
    private final AccInstructionOpcodeEnum enumer;

    public AccInstructionOpcode(AccInstructionOpcodeEnum enumer) {
        this.enumer = enumer;
    }

    public AccInstructionOpcodeEnum getEnumer() {
        return enumer;
    }

    @Override
    public int getNrRegisters() {
        return 0;
    }
}
