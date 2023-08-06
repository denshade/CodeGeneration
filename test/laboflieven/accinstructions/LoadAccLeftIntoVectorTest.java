package laboflieven.accinstructions;

import laboflieven.registers.Register;
import laboflieven.statements.VectorRegister;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoadAccLeftIntoVectorTest {

    @Test
    public void testVectorValue()
    {
        var instr = new LoadAccLeftIntoVector();
        Register left = new Register("left");
        left.value = 24; // 2^3 x 3^1
        Register right = new Register("right");
        VectorRegister leftVect = new VectorRegister ("left");
        VectorRegister rightVect = new VectorRegister ("right");
        instr.execute(left, right, leftVect, rightVect, 1);
        assertEquals(3, leftVect.value.get(0)); //2
        assertEquals(1, leftVect.value.get(1)); //3

    }

}