package laboflieven;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lieven on 14/06/2015.
 */
public class InOutParameters
{
    public InOutParameters() {
        input = new HashMap<>();
        expectedOutput = new HashMap<>();
    }

    public Map<String, Double> input;
    public Map<String, Double> expectedOutput;

}
