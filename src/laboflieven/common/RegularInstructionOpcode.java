package laboflieven.common;


import laboflieven.statements.RegularInstructionOpcodeEnum;

import java.util.Objects;

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
        return getEnumer().getNrRegisters();
    }

    @Override
    public String getName() {
        return getEnumer().name();
    }

    @Override
    public Object getEnumeration(){
        return enumer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegularInstructionOpcode that = (RegularInstructionOpcode) o;
        return enumer == that.enumer;
    }

    @Override
    public int hashCode() {
        return Objects.hash(enumer);
    }
}
