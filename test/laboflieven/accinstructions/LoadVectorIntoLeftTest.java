package laboflieven.accinstructions;

import laboflieven.statements.Register;
import laboflieven.statements.VectorRegister;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;

class LoadVectorIntoLeftTest {

    @Test
    void execute() {
        var v = new LoadVectorIntoLeft();
        Register left = new Register("left");
        Register right = new Register("right");
        VectorRegister leftVect = new VectorRegister ("left");
        leftVect.value = new Vector<>(List.of(3.0,1.0));
        VectorRegister rightVect = new VectorRegister ("right");
        v.execute(left, right, leftVect, rightVect, 1);
        assertEquals(2*2*2*3, left.value);
    }
}