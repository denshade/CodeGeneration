package laboflieven.instructions.logic;

import laboflieven.TestcaseInOutParameters;

import java.util.ArrayList;
import java.util.List;

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
            for (int k = 0; k < parts.length; k++) {
                String part = parts[k].replaceAll("\"", "");
                points.add(Boolean.parseBoolean(part));
            }
            collection.add(points);
        }
        return collection;
    }

}
