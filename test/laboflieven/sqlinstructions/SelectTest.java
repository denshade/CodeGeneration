package laboflieven.sqlinstructions;

import org.junit.jupiter.api.Test;

import java.util.BitSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SelectTest {

    @Test
    void checkToString()
    {
        BitSet selectedColumns = new BitSet();
        selectedColumns.set(0);
        var select = new Select(new ColumnSelection(selectedColumns, List.of("A1") ), new TableSelection(0, List.of("T1")));
        assertEquals("SELECT A1 FROM T1", select.toString());
    }

}