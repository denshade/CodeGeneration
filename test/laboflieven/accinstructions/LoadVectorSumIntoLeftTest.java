package laboflieven.accinstructions;

import laboflieven.statements.Register;
import laboflieven.statements.VectorRegister;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoadVectorSumIntoLeftTest {

    @Test
    void execute() {
        var v = new LoadVectorSumIntoLeft();
        Register left = new Register("L1");
        Register right = new Register("R1");
        VectorRegister leftV = new VectorRegister("L1");
        leftV.value.add(1.0);
        leftV.value.add(1.0);
        VectorRegister rightV = new VectorRegister("R1");
        v.execute(left, right, leftV, rightV, 1);
        assertEquals(2, left.value);
    }
}