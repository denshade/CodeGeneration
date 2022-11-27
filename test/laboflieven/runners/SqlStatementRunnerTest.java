package laboflieven.runners;

import laboflieven.Program;
import laboflieven.sqlinstructions.ColumnSelection;
import laboflieven.sqlinstructions.Select;
import laboflieven.sqlinstructions.TableSelection;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.BitSet;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SqlStatementRunnerTest {

    @Test
    void testSelect() throws SQLException {
        var runner = new SqlStatementRunner();
        BitSet selectedColumns = new BitSet();
        selectedColumns.set(0);
        Select select = new Select(new ColumnSelection(selectedColumns, List.of("id")), new TableSelection(0, List.of("A")));
        var results = runner.execute(new Program(List.of(select), List.of()), Map.of());

    }

}