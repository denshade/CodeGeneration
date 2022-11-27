package laboflieven.sqlinstructions;

import java.util.BitSet;
import java.util.List;

public class TableSelection
{
    private String selectedTable;

    public TableSelection(Integer selectedTable, List<String> tables) {
        if (selectedTable >= tables.size()) {
            throw new IllegalArgumentException("selectedTable > tables size " + selectedTable + " @ " + tables);
        }
        this.selectedTable = tables.get(selectedTable);
    }

    public String toString()
    {
        return selectedTable;
    }

}
