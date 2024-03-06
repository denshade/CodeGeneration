package laboflieven.runners;

import laboflieven.Program;
import laboflieven.db.DatabaseLoader;
import laboflieven.instructions.sqlinstructions.ColumnSelection;
import laboflieven.instructions.sqlinstructions.Select;
import laboflieven.instructions.sqlinstructions.TableSelection;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.BitSet;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SqlStatementRunnerTest {

    @Test
    void testSelect() throws Exception {
        clearDatabase();
        var connection = DriverManager.getConnection("jdbc:hsqldb:mem:mymemdb", "SA", "");
        var runner = new SqlStatementRunner(connection);
        var dbl = new DatabaseLoader();
        var map = Map.of("T1", List.of("id", "A2"), "T2", List.of("B1", "B2"));
        var inserts = Map.of("T1", List.of(new String[]{"A1", "A2"}, new String[]{"B1", "B2"}));
        dbl.load(connection, map, inserts);
        BitSet selectedColumns = new BitSet();
        selectedColumns.set(0);
        Select select = new Select(new ColumnSelection(selectedColumns, List.of("id")), new TableSelection(0, List.of("T1")));
        var results = runner.execute(new Program(List.of(select), List.of()), Map.of());

    }
    public void tearDown() {
        try {
            clearDatabase();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }


    public void clearDatabase() throws Exception {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:hsqldb:mem:mymemdb", "SA", "");
            try {
                Statement stmt = connection.createStatement();
                try {
                    stmt.execute("DROP SCHEMA PUBLIC CASCADE");
                    connection.commit();
                } finally {
                    stmt.close();
                }
            } catch (SQLException e) {
                connection.rollback();
                throw new Exception(e);
            }
        } catch (SQLException e) {
            throw new Exception(e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

}