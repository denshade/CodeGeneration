package laboflieven.instructions.sqlinstructions;

import laboflieven.InstructionMark;
import laboflieven.common.InstructionOpcode;

public class InnerJoin implements InstructionMark {
    @Override
    public InstructionOpcode getInstructionOpcode() {
        return new SQLInstructionOpcode(SqlInstructionEnum.INNER_JOIN);
    }
}
