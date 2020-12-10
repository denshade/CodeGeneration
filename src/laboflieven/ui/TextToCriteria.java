package laboflieven.ui;

import laboflieven.TestcaseInOutParameters;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lieven on 5-5-2016.
 */
public class TextToCriteria
{
    public List<TestcaseInOutParameters> parseMultipleStrings(String newLineSeparatedStrings) throws Exception {
        List<TestcaseInOutParameters> list = new ArrayList<>();
        for (String line : newLineSeparatedStrings.split("\n"))
        {
            list.add(createFromString(line));
        }
        return list;
    }

    public TestcaseInOutParameters createFromString(String line) throws Exception {
        TestcaseInOutParameters parameters = new TestcaseInOutParameters();
        if (line.split(";").length != 2) {
            throw new Exception("line has a valid ; format " + line);
        }
        String expectedString = line.split(";")[1];
        String inputString = line.split(";")[0];
        String[] inputItems = inputString.split(",");

        for (int i = 0; i < inputItems.length; i++) {
            Double ar = Double.parseDouble(inputItems[i]);
            parameters.input.put("r"+i, ar);
        }
        parameters.expectedOutput.put("r0", Double.parseDouble(expectedString));
        return parameters;
    }
}
