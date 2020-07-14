package laboflieven.common;

import laboflieven.accinstructions.AccInstructionOpcodeEnum;

import java.util.Objects;

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
        return getEnumer().getNrRegisters();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccInstructionOpcode that = (AccInstructionOpcode) o;
        return enumer == that.enumer;
    }

    @Override
    public int hashCode() {
        return Objects.hash(enumer);
    }
}
