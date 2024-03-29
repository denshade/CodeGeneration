package laboflieven.loggers;

import laboflieven.instructions.regular.Add;
import laboflieven.registers.Register;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CsvFileFitnessLoggerTest {

    @Test
    public void checkCsv() throws IOException {
        File f = File.createTempFile("pref", "suff");
        CsvFileFitnessLogger logger = new CsvFileFitnessLogger(f);
        logger.addFitness(List.of(new Add(new Register("R1"), new Register("R1"))), 2,2,3);
        logger.finish();
        List<String> s = Files.readAllLines(f.toPath());
        assertEquals("0;0;3.0", s.get(0));

        f.delete();

    }

}