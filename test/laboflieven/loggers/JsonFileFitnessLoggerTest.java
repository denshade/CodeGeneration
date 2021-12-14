package laboflieven.loggers;

import laboflieven.accinstructions.Add;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonFileFitnessLoggerTest {

    @Test
    void addFitness() throws IOException, ParseException {
        var temp = File.createTempFile("pre", ".csv");
        var logger = new JsonFileFitnessLogger(temp);
        logger.addFitness(List.of(new Add()), 3,3,1);
        logger.addFitness(List.of(new Add(), new Add()), 3,3,2);

        logger.finish();
        JSONParser parser = new JSONParser();
        assertTrue(temp.canRead());
        JSONObject obj = (JSONObject) parser.parse(new FileReader(temp));
        assertEquals("",obj.get("name"));
        assertTrue(obj.get("children") instanceof JSONArray);
        assertEquals(1, ((JSONArray) obj.get("children")).size());
        JSONObject firstAddInstruction = (JSONObject) ((JSONArray) obj.get("children")).get(0);
        assertEquals(1.0, firstAddInstruction.get("error"));
        assertEquals(2.0, ((JSONObject)((JSONArray) firstAddInstruction.get("children")).get(0)).get("error"));

    }
}