package laboflieven.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConfigurationTest {

    @Test
    void getHelp() {
        var config = new Configuration();
        assertNotNull(config.getHelp());
    }
}