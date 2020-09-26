package laboflieven.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommandLineConfigLoaderTest {

    @Test
    public void checkMaxInstruct()
    {
        String[] args = new String[] { Configuration.ConfigurationKey.MAX_NR_OF_INSTRUCTIONS + "=5"};
        CommandLineConfigLoader loader = new CommandLineConfigLoader();
        assertEquals(loader.loadFromCommandLine(args).getMaxNrInstructions(200),5);
    }

}