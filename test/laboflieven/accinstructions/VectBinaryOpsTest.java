package laboflieven.accinstructions;

import laboflieven.instructions.accinstructions.VectAdd;
import laboflieven.instructions.accinstructions.VectDiv;
import laboflieven.instructions.accinstructions.VectMul;
import laboflieven.instructions.accinstructions.VectSub;
import laboflieven.instructions.regular.VectorRegister;
import org.junit.jupiter.api.Test;

import java.util.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VectBinaryOpsTest {

    @Test
    void add() {
        var L = vect("L", 1, 2, 3);
        var R = vect("R", 10, 20, 30);
        new VectAdd().execute(null, null, L, R, 0);
        assertEquals(11.0, L.value.get(0));
        assertEquals(22.0, L.value.get(1));
        assertEquals(33.0, L.value.get(2));
    }

    @Test
    void sub() {
        var L = vect("L", 10, 20);
        var R = vect("R", 1, 2);
        new VectSub().execute(null, null, L, R, 0);
        assertEquals(9.0, L.value.get(0));
        assertEquals(18.0, L.value.get(1));
    }

    @Test
    void mul() {
        var L = vect("L", 2, 3);
        var R = vect("R", 4, 5);
        new VectMul().execute(null, null, L, R, 0);
        assertEquals(8.0, L.value.get(0));
        assertEquals(15.0, L.value.get(1));
    }

    @Test
    void div() {
        var L = vect("L", 12, 15);
        var R = vect("R", 3, 5);
        new VectDiv().execute(null, null, L, R, 0);
        assertEquals(4.0, L.value.get(0));
        assertEquals(3.0, L.value.get(1));
    }

    @Test
    void minLengthBoundsOperation() {
        var L = vect("L", 1, 2, 99);
        var R = vect("R", 10, 20);
        new VectAdd().execute(null, null, L, R, 0);
        assertEquals(11.0, L.value.get(0));
        assertEquals(22.0, L.value.get(1));
        assertEquals(99.0, L.value.get(2));
    }

    private static VectorRegister vect(String name, double... vals) {
        var v = new VectorRegister(name);
        v.value = new Vector<>();
        for (double x : vals) {
            v.value.add(x);
        }
        return v;
    }
}
