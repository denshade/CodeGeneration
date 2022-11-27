package laboflieven.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DatabaseLoader
{
    public String createTable(String table, List<String> columns) {
        var s = columns.stream().map(c -> c + " VARCHAR(255)").collect(Collectors.joining(", "));
        return "CREATE TABLE "+table + "(" + s+")";
    }
    public void load(Connection connection, Map<String, List<String>> tableToColumns) throws SQLException {
        Statement statement = connection.createStatement();
        boolean success = statement.execute("CREATE TABLE A(id INTEGER, value VARCHAR(255))");

    }
}
