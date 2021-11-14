package laboflieven.programiterators;

import laboflieven.InstructionMark;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class RandomGeneticProgramIteratorTest {

    @org.junit.jupiter.api.Test
    void createRandomInstructionRecursively() {
        var iterator = new RandomGeneticProgramIterator(null, 1000, 0.01, 0.01);
        var instructions = new ArrayList<InstructionMark>();
        iterator.createRandomInstructionRecursively(instructions);
    }
}