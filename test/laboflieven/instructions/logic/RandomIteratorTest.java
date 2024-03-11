package laboflieven.instructions.logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RandomIteratorTest {

    @Test
    void randomTree() {
        assertNotNull(RandomIterator.randomFormula());
    }

}