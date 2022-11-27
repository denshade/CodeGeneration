package laboflieven.db;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseLoaderTest {

    @Test
    void createTable() {
        String result = new DatabaseLoader().createTable("T1", List.of("A1", "A2", "A3"));
        assertEquals("CREATE TABLE T1(A1 VARCHAR(255), A2 VARCHAR(255), A3 VARCHAR(255))", result);
    }
}