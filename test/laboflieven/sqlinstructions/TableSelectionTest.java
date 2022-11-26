package laboflieven.sqlinstructions;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TableSelectionTest {

    @Test
    void testTableThrowsIfIndexTooBig()
    {
        assertThrows(IllegalArgumentException.class, () -> new TableSelection(1, List.of("T1")));
    }

    @Test
    void testTable()
    {
        assertEquals("T1", new TableSelection(0, List.of("T1")).toString());
        assertEquals("T2", new TableSelection(1, List.of("T1", "T2")).toString());

    }

}