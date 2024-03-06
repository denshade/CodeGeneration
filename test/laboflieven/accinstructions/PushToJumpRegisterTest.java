package laboflieven.accinstructions;

import laboflieven.instructions.accinstructions.PushToJumpRegister;
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
        Register l = new Register("l");
        r.value = 5;
        l.value = 5;
        var ip = p.execute(l, r, pushR, 12);
        assertEquals(5, pushR.value);
    }

}