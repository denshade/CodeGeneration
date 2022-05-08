package laboflieven.programiterators;

import laboflieven.common.Configuration;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiscreteDescentIteratorTest
{

    @Test
    void iterator()
    {
        var iterator = new DiscreteDescentIterator();
        var config = new Configuration();
        iterator.iterate(config);

    }

}