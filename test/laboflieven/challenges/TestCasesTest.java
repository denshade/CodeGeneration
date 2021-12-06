package laboflieven.challenges;

import laboflieven.TestcaseInOutParameters;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

class TestCasesTest {

    @Test
    void loadFromCsvFile() throws IOException {
        File file = new File("example.csv");
        Files.write(file.toPath(), ("data1,data2\n" +
                "2,4").getBytes());
        assertNotNull(TestCases.loadFromCsvFile(file));
        file.delete();

    }
    @Test
    void loadFromCsvString() {
        assertEquals(1, TestCases.loadFromCsvString("data1,data2\n" +
                                            "2,4", 1).size());
        TestcaseInOutParameters firstInOutParams = TestCases.loadFromCsvString("data1,data2\n" +
                "2,4", 1).get(0);
        assertEquals(4.0, firstInOutParams.input.get("R1"));
        assertEquals(2.0, firstInOutParams.expectedOutput.get("R1"));
        assertEquals(2.0, TestCases.loadFromCsvString("data1,data2\n" +
                "2,4", 2).get(0).input.get("R1"));
        assertEquals(4.0, TestCases.loadFromCsvString("data1,data2\n" +
                "2,4", 2).get(0).expectedOutput.get("R1"));
    }
}