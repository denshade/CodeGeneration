package laboflieven.db;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseLoaderTest {

    @Test
    void createTable() {
        String result = new DatabaseLoader().createTable("T1", List.of("A1", "A2", "A3"));
        assertEquals("CREATE TABLE T1(A1 VARCHAR(255), A2 VARCHAR(255), A3 VARCHAR(255))", result);
    }
    @Test
    void createTables() {
        var map = Map.of("T1", List.of("A1", "A2", "A3"), "T2", List.of("B1", "B2", "B3"));
        var result = new DatabaseLoader().createTables(map);
        assertTrue(result.contains("CREATE TABLE T1(A1 VARCHAR(255), A2 VARCHAR(255), A3 VARCHAR(255))"));
        assertTrue(result.contains("CREATE TABLE T2(B1 VARCHAR(255), B2 VARCHAR(255), B3 VARCHAR(255))"));
    }

}