package laboflieven.accinstructions;

import laboflieven.instructions.accinstructions.Jump2IfEq;
import laboflieven.registers.Register;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Jump2IfEqTest {
    @Test
    void check() {
        var j = new Jump2IfEq();
        var ip = j.execute(new Register("L", 1.0), new Register("R", 2.0), new Register("L", 3.0),0);
        assertNull( ip );
        ip = j.execute(new Register("L", 1.0), new Register("R", 1.0), new Register("L", 3.0),0);
        assertEquals(3,  ip );
    }
}