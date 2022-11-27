package laboflieven.runners;

import laboflieven.Program;
import laboflieven.sqlinstructions.ColumnSelection;
import laboflieven.sqlinstructions.Select;
import laboflieven.sqlinstructions.TableSelection;

import java.sql.*;
import java.util.BitSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SqlStatementRunner
{
    private final Connection connection;
    public SqlStatementRunner() throws SQLException {
        connection = DriverManager.getConnection("jdbc:hsqldb:mem:mymemdb", "SA", "");
        Statement statement = connection.createStatement();
        boolean success = statement.execute("CREATE TABLE A(id INTEGER, value VARCHAR(255))");
    }

    protected void finalize() throws SQLException {
        connection.close();
    }
    public ResultSet execute(Program program, Map<String, Double> registerValues) throws SQLException {
        String SQLStatement = program.getInstructions().stream().map(Object::toString).collect(Collectors.joining());
        Statement statement = connection.createStatement();
        return statement.executeQuery(SQLStatement);
    }

    public static void main(String[] args) {
        try {
            var runner = new SqlStatementRunner();
            BitSet selectedColumns = new BitSet();
            selectedColumns.set(0);
            Select select = new Select(new ColumnSelection(selectedColumns, List.of("id")), new TableSelection(0, List.of("A")));
            runner.execute(new Program(List.of(select), List.of()), Map.of());
        } catch(Exception exception) {
            System.out.println(exception);
        }
    }
}
