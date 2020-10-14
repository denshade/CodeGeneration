package laboflieven.programiterators;

import laboflieven.challenges.GeneralFinder;
import laboflieven.common.Configuration;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PriorityProgramIteratorTest {

    @Test
    void iterate() {
        String[] arguments =
                (       Configuration.ConfigurationKey.MAX_NR_OF_INSTRUCTIONS + "=4 " +
                        Configuration.ConfigurationKey.NR_REGISTERS + "=2 "+
                        Configuration.ConfigurationKey.INSTRUCTION_FACTORY +"=Acc " +
                        Configuration.ConfigurationKey.DATA_PROVIDER + "=laboflieven.challenges.AdderFinder " +
                        Configuration.ConfigurationKey.PROGRAM_ITERATOR + "=priority"
                ).split(" ");
        GeneralFinder.loadConfigAndRun(arguments);
    }
}