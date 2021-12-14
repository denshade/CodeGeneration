package laboflieven.challenges;

import laboflieven.InstructionMark;
import laboflieven.loggers.CsvFileFitnessLogger;
import laboflieven.statements.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
        CsvFileFitnessLogger l = new CsvFileFitnessLogger(new java.io.File("f.csv"));
        assertNotNull(l.getXandY(instructions, RegularInstructionOpcodeEnum.values().length, 2));
    }

    @Test
    public void registerToInt() throws IOException {
        Register r = new Register("R1");
        CsvFileFitnessLogger l = new CsvFileFitnessLogger(new java.io.File("f.csv"));
        assertEquals(2, l.registerToInt(r.name));

    }
}