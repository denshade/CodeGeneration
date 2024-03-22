package laboflieven.instructions.logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BooleanCsvSourceTest {
    @Test
    void checkLoadsData() {
        var csvRows = """
                true,false,true
                """;
        var booleanCsvSource = new BooleanCsvSource();
        var data = booleanCsvSource.loadFromCsvString(csvRows, false);
        assertEquals( true, data.get(0).get(0));
        assertEquals( false, data.get(0).get(1));
    }

}