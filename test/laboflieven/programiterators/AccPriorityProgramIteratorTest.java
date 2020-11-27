package laboflieven.programiterators;

import laboflieven.common.Configuration;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccPriorityProgramIteratorTest {

    @Test
    void iterate() {
        Configuration conf = new Configuration();
        var iterator = new AccPriorityProgramIterator();
        assertThrows(IllegalArgumentException.class, () -> iterator.iterate(conf));
    }
}