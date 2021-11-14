package laboflieven.programiterators;

import laboflieven.InstructionMark;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;

class RandomGeneticProgramIteratorTest {

    @Test
    void createRandomInstructionRecursively() {
        var iterator = new RandomGeneticProgramIterator(null, 1000, 0.01, 0.01);
        var instructions = new ArrayList<InstructionMark>();
        iterator.createRandomInstructionRecursively(instructions);
        assertFalse(iterator.chosenSolutions.isEmpty());
    }
}