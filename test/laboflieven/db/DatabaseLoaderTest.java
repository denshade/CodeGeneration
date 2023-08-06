package laboflieven.db;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Test
    void createInserts() {
        var inserts = new DatabaseLoader().insertSqls("T1", List.of(new String[]{"A1", "A2"}, new String[]{"B1", "B2"}));
        assertTrue(inserts.contains("INSERT INTO T1 VALUES('A1','A2')"));
        assertTrue(inserts.contains("INSERT INTO T1 VALUES('B1','B2')"));
    }

    @Test
    void createInsertsMap() {
        var map = Map.of("T1", List.of(new String[]{"A1", "A2"}, new String[]{"B1", "B2"}));
        var inserts = new DatabaseLoader().insertSqlMap(map);
        assertTrue(inserts.contains("INSERT INTO T1 VALUES('A1','A2')"));
        assertTrue(inserts.contains("INSERT INTO T1 VALUES('B1','B2')"));
    }

    @Test
    @Disabled
    void testDatabase() throws SQLException {
        var connection = DriverManager.getConnection("jdbc:hsqldb:mem:mymemdb", "SA", "");
        var dbl = new DatabaseLoader();
        var map = Map.of("T1", List.of("A1", "A2"), "T2", List.of("B1", "B2"));
        var inserts = Map.of("T1", List.of(new String[]{"A1", "A2"}, new String[]{"B1", "B2"}));
        dbl.load(connection, map, inserts);
        ResultSet set = connection.createStatement().executeQuery("SELECT * from T1");
        var allRows = new ArrayList<String>();
        int numberColumns = 2;
        while(set.next()){
            String[] currentRow = new String[numberColumns];
            for(int i = 1;i<=numberColumns;i++){
                currentRow[i-1]=set.getString(i);
            }
            allRows.add(String.join(".", currentRow));
        }
        assertEquals("[A1.A2, B1.B2]", allRows.toString());
        connection.close();
    }

}