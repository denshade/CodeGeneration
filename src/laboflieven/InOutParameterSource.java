package laboflieven;

import java.util.List;

public interface InOutParameterSource {
    List<TestcaseInOutParameters> getInOutParameters(int curMaxRegisters);
}
