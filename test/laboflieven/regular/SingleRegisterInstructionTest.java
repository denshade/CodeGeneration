package laboflieven.regular;


import laboflieven.instructions.regular.SingleRegisterInstruction;
import laboflieven.instructions.regular.Sqrt;
import laboflieven.registers.Register;
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