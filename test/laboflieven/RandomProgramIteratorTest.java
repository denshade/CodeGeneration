package laboflieven;

import laboflieven.statements.Cos;
import laboflieven.statements.Instruction;
import laboflieven.statements.Move;
import laboflieven.statements.Register;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class RandomProgramIteratorTest
{
    @Test
    public void testdifference() {
        ArrayList<InstructionMark> instructions = new ArrayList<>();
        instructions.add(new Move(new Register("r1"), new Register("r2")));
        instructions.add(new Move(new Register("r1"), new Register("r2")));
        instructions.add(new Cos(new Register("r1")));
        assertEquals(2, RandomProgramIterator.difference(instructions));
    }


}