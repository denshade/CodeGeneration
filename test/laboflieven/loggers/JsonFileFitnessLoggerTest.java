package laboflieven.loggers;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class JsonFileFitnessLoggerTest {

    @Test
    void addFitness() throws IOException, ParseException {
        var temp = File.createTempFile("pre", ".csv");
        var logger = new JsonFileFitnessLogger(temp);
        logger.finish();
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(temp));

        assertTrue(temp.canRead());

    }
}