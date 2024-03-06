package laboflieven.runners;

import laboflieven.Program;
import laboflieven.instructions.sqlinstructions.ColumnSelection;
import laboflieven.instructions.sqlinstructions.Select;
import laboflieven.instructions.sqlinstructions.TableSelection;

import java.sql.*;
import java.util.BitSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SqlStatementRunner
{
    private final Connection connection;
    public SqlStatementRunner(Connection connection) throws SQLException {
        this.connection = connection;
    }

    public ResultSet execute(Program program, Map<String, Double> registerValues) throws SQLException {
        String SQLStatement = program.getInstructions().stream().map(Object::toString).collect(Collectors.joining());
        Statement statement = connection.createStatement();
        return statement.executeQuery(SQLStatement);
    }

    public static void main(String[] args) {
        try {
            var connection = DriverManager.getConnection("jdbc:hsqldb:mem:mymemdb", "SA", "");
            var runner = new SqlStatementRunner(connection);
            BitSet selectedColumns = new BitSet();
            selectedColumns.set(0);
            Select select = new Select(new ColumnSelection(selectedColumns, List.of("id")), new TableSelection(0, List.of("A")));
            runner.execute(new Program(List.of(select), List.of()), Map.of());
        } catch(Exception exception) {
            System.out.println(exception);
        }
    }
}
