package laboflieven.statements;

import junit.framework.TestCase;

/**
 * Created by Lieven on 6/07/2015.
 */
public class SingleRegisterInstructionTest extends TestCase {

    public void testToString() throws Exception {
        SingleRegisterInstruction i = new Sqrt(new Register("r1"));
        assertEquals("Sqrt r1", i.toString());
    }
}