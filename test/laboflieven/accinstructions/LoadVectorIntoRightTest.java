package laboflieven.accinstructions;

import laboflieven.registers.Register;
import laboflieven.statements.VectorRegister;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;

class LoadVectorIntoRightTest {

    @Test
    void execute() {
        var v = new LoadVectorIntoRight();
        Register left = new Register("left");
        Register right = new Register("right");
        VectorRegister leftVect = new VectorRegister ("left");
        VectorRegister rightVect = new VectorRegister ("right");
        rightVect.value = new Vector<>(List.of(3.0,1.0));
        v.execute(left, right, leftVect, rightVect, 1);
        assertEquals(2*2*2*3, right.value);
    }
}