package laboflieven.instructions.sqlinstructions;

import laboflieven.common.InstructionOpcode;

public class SQLInstructionOpcode implements InstructionOpcode {
    private final SqlInstructionEnum enumer;

    public SQLInstructionOpcode(SqlInstructionEnum enumer) {
        this.enumer = enumer;
    }


    @Override
    public int getNrRegisters() {
        return 0;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Object getEnumeration() {
        return enumer;
    }
}
