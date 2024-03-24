package laboflieven.instructions.logic;

import laboflieven.TestcaseInOutParameters;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BooleanCsvSource {

    //All
    public List<List<Boolean>> loadFromCsvString(String csvData, boolean skipFirstLine) {
        List<List<Boolean>> collection = new ArrayList<>();
        String[] lines = csvData.split("\n");
        boolean isFirstLine = skipFirstLine;
        for (String line : lines) {
            if (isFirstLine) {
                isFirstLine = false;
                continue;
            }
            String[] parts = line.split(",");
            List<Boolean> points = new ArrayList<>();
            for (String s : parts) {
                String part = s.replaceAll("\"", "").toLowerCase(Locale.ROOT).trim();
                boolean value = switch (part) {
                    case "true", "1" -> true;
                    case "false", "0" -> false;
                    default -> throw new IllegalArgumentException("Invalid value " + part + " expecting true, false or 0, 1");
                };
                points.add(value);
            }
            collection.add(points);
        }
        return collection;
    }

}
