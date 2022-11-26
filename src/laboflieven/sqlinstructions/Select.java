package laboflieven.sqlinstructions;

import laboflieven.InstructionMark;
import laboflieven.common.InstructionOpcode;

public class Select implements InstructionMark {
    public Select(ColumnSelection columnSelection){

    }
    @Override
    public InstructionOpcode getInstructionOpcode() {
        return null;
    }
}
