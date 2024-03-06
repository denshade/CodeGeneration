package laboflieven.sqlinstructions;

import laboflieven.instructions.sqlinstructions.ColumnSelection;
import org.junit.jupiter.api.Test;

import java.util.BitSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ColumnSelectionTest {

    @Test
    void testEmptyColumnSelectionThrows() {
        assertThrows(IllegalArgumentException.class, () -> new ColumnSelection(new BitSet(), List.of("nothing")));
    }
    @Test
    void testOverflowColumnSelectionThrows() {
        var set = new BitSet(2);
        set.set(2);
        assertThrows(IllegalArgumentException.class, () -> new ColumnSelection(set, List.of("nothing")));
    }
    @Test
    void testColumnSelectionFinds() {
        var set = new BitSet(2);
        set.set(0);
        var selection = new ColumnSelection(set, List.of("nothing"));
        assertEquals("nothing",selection.toString());
    }
}