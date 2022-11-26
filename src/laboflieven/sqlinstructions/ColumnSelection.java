package laboflieven.sqlinstructions;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.stream.Collectors;

public class ColumnSelection
{
    private final List<String> selectedColumnList = new ArrayList<>();

    ColumnSelection(BitSet selectedColumns, List<String> columns) {
        if (selectedColumns.nextSetBit(0) == -1) {
            throw new IllegalArgumentException("No selected columns for "+columns);
        }
        for (int nextIndex = selectedColumns.nextSetBit(0); nextIndex != -1 ; nextIndex = selectedColumns.nextSetBit(nextIndex + 1)) {
            if (nextIndex >= columns.size()) {
                throw new IllegalArgumentException("No selected columns for "+columns);
            }
            selectedColumnList.add(columns.get(nextIndex));
        }
    }

    public String toString()
    {
        return selectedColumnList.stream().collect(Collectors.joining(","));
    }

}
