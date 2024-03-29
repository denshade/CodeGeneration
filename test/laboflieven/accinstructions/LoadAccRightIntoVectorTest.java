package laboflieven.accinstructions;

import laboflieven.instructions.accinstructions.LoadAccRightIntoVector;
import laboflieven.registers.Register;
import laboflieven.instructions.regular.VectorRegister;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LoadAccRightIntoVectorTest {
    @Test
    void execute() {
        var instr = new LoadAccRightIntoVector();
        Register left = new Register("left");
        Register right = new Register("right");
        right.value = 24; // 2^3 x 3^1
        VectorRegister leftVect = new VectorRegister ("left");
        VectorRegister rightVect = new VectorRegister ("right");
        instr.execute(left, right, leftVect, rightVect, 1);
        assertEquals(3, rightVect.value.get(0)); //2
        assertEquals(1, rightVect.value.get(1)); //3
    }

    @Test
    void executePrime() {
        var instr = new LoadAccRightIntoVector();
        Register left = new Register("left");
        Register right = new Register("right");
        right.value = 3; // 2^3 x 3^1
        VectorRegister leftVect = new VectorRegister ("left");
        VectorRegister rightVect = new VectorRegister ("right");
        instr.execute(left, right, leftVect, rightVect, 1);
        assertEquals(0, rightVect.value.get(0)); //2
        assertEquals(1, rightVect.value.get(1)); //3
    }

    @Test
    void executeFloat() {
        var instr = new LoadAccRightIntoVector();
        Register left = new Register("left");
        Register right = new Register("right");
        right.value = 3.1445546; // 2^3 x 3^1
        VectorRegister leftVect = new VectorRegister ("left");
        VectorRegister rightVect = new VectorRegister ("right");
        instr.execute(left, right, leftVect, rightVect, 1);
        assertEquals(0, rightVect.value.size());
    }
}