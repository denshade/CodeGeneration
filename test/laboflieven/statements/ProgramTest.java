package laboflieven;

import junit.framework.TestCase;
import laboflieven.statements.Instruction;
import laboflieven.statements.Register;
import laboflieven.statements.Sqrt;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lieven on 6/07/2015.
 */
public class ProgramTest extends TestCase {

    public void testToString() throws Exception {
        List< Instruction> instructions = new ArrayList<>();
        List<Register> registers = new ArrayList<>();
        Register eax = new Register("EAX");
        registers.add(eax);
        instructions.add(new Sqrt(eax));
        Program p = new Program(instructions, registers);
        assertEquals("[Sqrt EAX]", p.toString());
    }
}