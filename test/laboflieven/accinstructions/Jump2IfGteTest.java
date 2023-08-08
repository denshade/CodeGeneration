package laboflieven.accinstructions;

import laboflieven.registers.Register;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Jump2IfGteTest {

    @Test
    void jump2IfGte(){
        var kr = new Jump2IfGte();
        var regL = new Register("L", 4);
        var regR = new Register("R", 2);
        var regJ = new Register("J", +5);

        var ip = kr.execute(regL, regR, regJ, 1);
        assertEquals(6, ip);
    }

}