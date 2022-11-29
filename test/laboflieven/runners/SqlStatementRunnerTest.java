package laboflieven.runners;

import laboflieven.Program;
import laboflieven.db.DatabaseLoader;
import laboflieven.sqlinstructions.ColumnSelection;
import laboflieven.sqlinstructions.Select;
import laboflieven.sqlinstructions.TableSelection;
import org.junit.jupiter.api.Test;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.BitSet;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SqlStatementRunnerTest {

    @Test
    void testSelect() throws SQLException {
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

}