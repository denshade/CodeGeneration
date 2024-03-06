package laboflieven.regular;

import laboflieven.InstructionMark;
import laboflieven.Program;
import laboflieven.instructions.regular.Sqrt;
import laboflieven.registers.Register;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by Lieven on 6/07/2015.
 */
public class ProgramTest  {

    public void testToString() throws Exception {
        List<InstructionMark> instructions = new ArrayList<>();
        List<Register> registers = new ArrayList<>();
        Register eax = new Register("EAX");
        registers.add(eax);
        instructions.add(new Sqrt(eax));
        Program p = new Program(instructions, registers);
        assertEquals("[Sqrt EAX]", p.toString());
    }
}