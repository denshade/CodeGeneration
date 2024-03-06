package laboflieven.instructions.sqlinstructions;

import laboflieven.InstructionMark;
import laboflieven.common.InstructionOpcode;

public class Where implements InstructionMark {
    @Override
    public InstructionOpcode getInstructionOpcode() {
        return new SQLInstructionOpcode(SqlInstructionEnum.WHERE);
    }
}
