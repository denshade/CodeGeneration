package laboflieven.accinstructions;

import laboflieven.statements.VectorRegister;
import org.junit.jupiter.api.Test;

import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;

class LeftVectShiftTest {

    @Test
    void execute() {
        var l = new LeftVectShift();
        var leftVect = new VectorRegister("L1");
        leftVect.value = new Vector<>();
        leftVect.value.add(2.0);
        leftVect.value.add(3.0);
        l.execute(null, null, leftVect, null,1);
        assertEquals(1, leftVect.value.size());
        assertEquals(3.0, leftVect.value.get(0));
    }


    @Test
    void executeShiftOfEmpty() {
        var l = new LeftVectShift();
        var leftVect = new VectorRegister("L1");
        leftVect.value = new Vector<>();
        l.execute(null, null, leftVect, null,1);
        assertEquals(0, leftVect.value.size());
    }
}