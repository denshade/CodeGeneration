package laboflieven.sqlinstructions;

import laboflieven.InstructionMark;
import laboflieven.common.InstructionOpcode;

public class Select implements InstructionMark {
    private final ColumnSelection columnSelection;
    private final TableSelection tableSelection;

    public Select(ColumnSelection columnSelection, TableSelection tableSelection){
        this.columnSelection = columnSelection;
        this.tableSelection = tableSelection;
    }
    @Override
    public InstructionOpcode getInstructionOpcode() {
        return new SQLInstructionOpcode(SqlInstructionEnum.SELECT);
    }
    public String toString()
    {
        return "SELECT " + columnSelection.toString() + " FROM " + tableSelection.toString();
    }
}
