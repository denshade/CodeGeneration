package laboflieven.statements;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by Lieven on 6/07/2015.
 */
public class SingleRegisterInstructionTest  {

    @Test
    public void testToString() throws Exception {
        SingleRegisterInstruction i = new Sqrt(new Register("r1"));
        assertEquals("Sqrt r1", i.toString());
    }
}