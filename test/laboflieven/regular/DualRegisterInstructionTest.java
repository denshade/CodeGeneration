package laboflieven.regular;


import laboflieven.instructions.regular.DualRegisterInstruction;
import laboflieven.instructions.regular.Move;
import laboflieven.registers.Register;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by Lieven on 6/07/2015.
 */
public class DualRegisterInstructionTest  {

    @Test
    public void testToString() throws Exception {
        Register eax = new Register("eax");
        Register ebx = new Register("ebx");

        DualRegisterInstruction dialRegister = new Move(eax, ebx);
        assertEquals("Move eax -> ebx", dialRegister.toString());
    }
}