package laboflieven.programiterators;

import laboflieven.common.Configuration;
import laboflieven.examiners.ProgramFitnessExaminer;
import laboflieven.runners.RegularStatementRunner;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DiscreteDescentIteratorTest
{

    @Test
    void iterator()
    {
        var iterator = new DiscreteDescentIterator();
        var config = new Configuration();
        config.setFitnessExaminer(new ProgramFitnessExaminer(List.of(),new RegularStatementRunner()));
        iterator.iterate(config);

    }

}