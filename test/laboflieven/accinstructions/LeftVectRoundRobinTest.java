package laboflieven.accinstructions;

import laboflieven.instructions.accinstructions.LeftVectRoundRobin;
import laboflieven.instructions.regular.VectorRegister;
import org.junit.jupiter.api.Test;

import java.util.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LeftVectRoundRobinTest {

    @Test
    void rotatesLastToFront() {
        var op = new LeftVectRoundRobin();
        var leftVect = new VectorRegister("L1");
        leftVect.value = new Vector<>();
        leftVect.value.add(1.0);
        leftVect.value.add(2.0);
        leftVect.value.add(3.0);
        leftVect.value.add(4.0);

        op.execute(null, null, leftVect, null, 1);

        assertEquals(4, leftVect.value.size());
        assertEquals(4.0, leftVect.value.get(0));
        assertEquals(1.0, leftVect.value.get(1));
        assertEquals(2.0, leftVect.value.get(2));
        assertEquals(3.0, leftVect.value.get(3));
    }

    @Test
    void emptyOrSingletonNoOp() {
        var op = new LeftVectRoundRobin();
        var empty = new VectorRegister("E");
        op.execute(null, null, empty, null, 1);
        assertEquals(0, empty.value.size());

        var one = new VectorRegister("O");
        one.value.add(9.0);
        op.execute(null, null, one, null, 1);
        assertEquals(1, one.value.size());
        assertEquals(9.0, one.value.get(0));
    }
}
