package laboflieven.accinstructions;

import laboflieven.instructions.accinstructions.LeftVectPushExponents;
import laboflieven.instructions.regular.VectorRegister;
import org.junit.jupiter.api.Test;

import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;

class LeftVectPushExponentsTest {

    @Test
    void execute() {
        var l = new LeftVectPushExponents();
        var leftVect = new VectorRegister("L1");
        leftVect.value = new Vector<>();
        leftVect.value.add(2.0);
        leftVect.value.add(3.0);
        l.execute(null, null, leftVect, null,1);
        assertEquals(3, leftVect.value.size());
        assertEquals(0.0, leftVect.value.get(0));
        assertEquals(2.0, leftVect.value.get(1));
        assertEquals(3.0, leftVect.value.get(2));
    }
}