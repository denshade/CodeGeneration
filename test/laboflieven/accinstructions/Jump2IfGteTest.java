package laboflieven.accinstructions;

import laboflieven.instructions.accinstructions.Jump2IfGte;
import laboflieven.registers.Register;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Jump2IfGteTest {

    @Test
    void check() {
        var j = new Jump2IfGte();
        var ip = j.execute(new Register("L", 1.0), new Register("R", 2.0), new Register("L", 3.0),0);
        assertNull( ip );
        ip = j.execute(new Register("L", 1.0), new Register("R", 1.0), new Register("L", 3.0),0);
        assertEquals(3,  ip );
    }

}