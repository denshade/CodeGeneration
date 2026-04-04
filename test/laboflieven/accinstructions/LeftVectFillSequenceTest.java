package laboflieven.accinstructions;

import laboflieven.instructions.accinstructions.LeftVectFillSequence;
import laboflieven.instructions.regular.VectorRegister;
import laboflieven.registers.Register;
import org.junit.jupiter.api.Test;

import java.util.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LeftVectFillSequenceTest {

    @Test
    void fillsOneToNFromLeftScalar() {
        var left = new Register("AL");
        left.value = 5;
        var lv = new VectorRegister("LV");
        lv.value.add(99.0);
        new LeftVectFillSequence().execute(left, null, lv, null, 0);
        assertEquals(5, lv.value.size());
        assertEquals(1.0, lv.value.get(0));
        assertEquals(5.0, lv.value.get(4));
    }

    @Test
    void floorsNonIntegerLeft() {
        var left = new Register("AL");
        left.value = 3.7;
        var lv = new VectorRegister("LV");
        new LeftVectFillSequence().execute(left, null, lv, null, 0);
        assertEquals(3, lv.value.size());
        assertEquals(2.0, lv.value.get(1));
    }

    @Test
    void nonPositiveOrBadLeftClears() {
        var lv = new VectorRegister("LV");
        lv.value = new Vector<>();
        lv.value.add(1.0);
        var op = new LeftVectFillSequence();
        var L = new Register("AL");
        L.value = 0;
        op.execute(L, null, lv, null, 0);
        assertTrue(lv.value.isEmpty());

        L.value = -3;
        lv.value.add(1.0);
        op.execute(L, null, lv, null, 0);
        assertTrue(lv.value.isEmpty());

        L.value = Double.NaN;
        lv.value.add(1.0);
        op.execute(L, null, lv, null, 0);
        assertTrue(lv.value.isEmpty());
    }
}
