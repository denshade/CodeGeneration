package laboflieven.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConfigurationTest {

    @Test
    void getCsv()
    {
        CommandLineConfigLoader loader = new CommandLineConfigLoader();
        var c = loader.loadFromCommandLine(new String[]{"CSV_FILE=test.csv"});
        assertEquals("test.csv", c.getCsvFile("banan"));
    }

    @Test
    void getCsvEmptyUsesDefault()
    {
        CommandLineConfigLoader loader = new CommandLineConfigLoader();
        var c = loader.loadFromCommandLine(new String[]{});
        assertEquals("default.csv", c.getCsvFile("default.csv"));
    }

    @Test
    void getCsvRandomIterator()
    {
        CommandLineConfigLoader loader = new CommandLineConfigLoader();
        var c = loader.loadFromCommandLine(new String[]{});
        assertEquals("default.csv", c.getCsvFile("default.csv"));
    }


    @Test
    void getHelp() {
        var config = new Configuration();
        assertNotNull(config.getHelp());
    }
}