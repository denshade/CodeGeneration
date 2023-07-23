package laboflieven.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DatabaseLoader
{
    public String createTable(String table, List<String> columns) {
        var s = columns.stream().map(c -> c + " VARCHAR(255)").collect(Collectors.joining(", "));
        return "CREATE TABLE " + table + "(" + s+")";
    }
    public List<String> createTables(Map<String, List<String>> tableToColumns)
    {
        var sqlStatements = new ArrayList<String>();
        for (var entry : tableToColumns.entrySet()) {
            sqlStatements.add(createTable(entry.getKey(), entry.getValue()));
        }
        return sqlStatements;
    }
    public List<String> insertSqls(String table, List<String[]> data) {
        return data.stream()
                .map(dataRow -> Stream.of(dataRow)
                        .map(d -> "'" + d + "'")
                        .collect(Collectors.joining(",")))
                .map(values -> "INSERT INTO " + table + " VALUES(" + values + ")")
                .collect(Collectors.toList());
    }
    public List<String> insertSqlMap(Map<String, List<String[]>> map) {
        var results = new ArrayList<String>();
        for (var m: map.entrySet())
        {
            results.addAll(insertSqls(m.getKey(), m.getValue()));
        }
        return results;
    }

    public void load(Connection connection, Map<String, List<String>> tableToColumns, Map<String, List<String[]>> map) throws SQLException {
        Statement statement = connection.createStatement();
        for (String sql : createTables(tableToColumns)){
            boolean success = statement.execute(sql);
        }
        for (String sql : insertSqlMap(map)){
            boolean success = statement.execute(sql);
        }
    }
}
