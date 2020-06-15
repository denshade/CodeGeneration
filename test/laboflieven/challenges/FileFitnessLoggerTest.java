package laboflieven.challenges;

import laboflieven.InstructionMark;
import laboflieven.loggers.FileFitnessLogger;
import laboflieven.statements.*;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class FileFitnessLoggerTest {

    @Test
    public void getXandY() throws IOException {
        Register r0 = new Register("R0");
        Register r1 = new Register("R1");

        List<InstructionMark> instructions = new ArrayList<>();
        Instruction mov = new Move(r0, r1);
        instructions.add(mov);
        Instruction add = new Add(r0, r1);
        instructions.add(add);
        FileFitnessLogger l = new FileFitnessLogger(new java.io.File("f.csv"));
        assertNotNull(l.getXandY(instructions, InstructionEnum.values().length, 2));
    }

    @Test
    public void registerToInt() throws IOException {
        Register r = new Register("R1");
        FileFitnessLogger l = new FileFitnessLogger(new java.io.File("f.csv"));
        assertEquals(2, l.registerToInt(r.name));

    }
}