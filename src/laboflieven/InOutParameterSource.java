package laboflieven;

import java.util.List;

public interface InOutParameterSource {
    public List<TestcaseInOutParameters> getInOutParameters(int curMaxRegisters);
}
