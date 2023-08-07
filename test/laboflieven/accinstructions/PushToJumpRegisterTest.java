package laboflieven.accinstructions;

import laboflieven.registers.Register;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PushToJumpRegisterTest
{
    @Test
    void checkPushPushesToJumpRegister()
    {
        var p = new PushToJumpRegister();
        Register pushR = new Register("push");
        pushR.value = -5;
        Register r = new Register("r");
        r.value = 5;
        var ip = p.execute(null, r, pushR, 12);
        assertEquals(5, pushR.value);
    }

}