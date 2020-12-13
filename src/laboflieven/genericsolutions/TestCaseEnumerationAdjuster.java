package laboflieven.genericsolutions;

import laboflieven.TestcaseInOutParameters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TestCaseEnumerationAdjuster
{
    public List<TestcaseInOutParameters> adjustTestcases(List<TestcaseInOutParameters> testcases) {
        return adjustTestcases(testcases, IntStream.range(0,testcases.size()).boxed().collect(Collectors.toList()));
    }
    public List<TestcaseInOutParameters> adjustTestcases(List<TestcaseInOutParameters> testcases, List<Integer> programIndex) {
        var parameters = new ArrayList<TestcaseInOutParameters>();
        int counter = 0;
        for (TestcaseInOutParameters param : testcases) {
            var scenario = new TestcaseInOutParameters();
            scenario.expectedOutput.put("R1", (double) programIndex.get(counter++));
            scenario.input = new HashMap<>(param.input);
            parameters.add(scenario);
        }
        return parameters;
    }
}
