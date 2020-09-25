package laboflieven.common;

import java.util.HashMap;

public class Configuration {

    private static Configuration instance;
    public static Configuration getInstance()
    {
        if (instance == null) {
            instance = new Configuration();
        }
        return instance;
    }

    private HashMap<String, Object> configurationSettings = new HashMap<>();
    public static String MAX_NR_OF_INSTRUCTIONS = "max_nr_instructions";

    public int getMaxNrInstructions(int defaultValue)
    {
        if (!configurationSettings.containsKey(MAX_NR_OF_INSTRUCTIONS))
        {
            return defaultValue;
        }
        return (int)configurationSettings.get(MAX_NR_OF_INSTRUCTIONS);
    }

    public void setMaxNrInstructions(int nrInstructions) {
        configurationSettings.put(MAX_NR_OF_INSTRUCTIONS, nrInstructions);
    }

}
