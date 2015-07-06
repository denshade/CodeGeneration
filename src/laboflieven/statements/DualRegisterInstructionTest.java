package laboflieven.statements;

import junit.framework.TestCase;

/**
 * Created by Lieven on 6/07/2015.
 */
public class DualRegisterInstructionTest extends TestCase {

    public void testToString() throws Exception {
        Register eax = new Register("eax");
        Register ebx = new Register("ebx");

        DualRegisterInstruction dialRegister = new Move(eax, ebx);
        assertEquals("Move eax -> ebx", dialRegister.toString());
    }
}