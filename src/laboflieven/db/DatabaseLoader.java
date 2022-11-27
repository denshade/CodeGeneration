package laboflieven.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DatabaseLoader
{
    public String createTable(String table, List<String> columns) {
        var s = columns.stream().map(c -> c + " VARCHAR(255)").collect(Collectors.joining(", "));
        return "CREATE TABLE "+table + "(" + s+")";
    }
    public List<String> createTables(Map<String, List<String>> tableToColumns)
    {
        var sqlStatements = new ArrayList<String>();
        for (var entry : tableToColumns.entrySet()) {
            sqlStatements.add(createTable(entry.getKey(), entry.getValue()));
        }
        return sqlStatements;
    }
    public void load(Connection connection, Map<String, List<String>> tableToColumns, List<String[]> data) throws SQLException {
        Statement statement = connection.createStatement();
        for (String sql : createTables(tableToColumns)){
            boolean success = statement.execute(sql);
        }

    }
}
