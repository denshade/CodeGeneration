package laboflieven.instructions.logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BruteForceRunnerTest {

    @Test
    void checkCsvSourceBrute() {
        var runner = new BruteForceRunner();
        var outcome = runner.loadFromString("""
true,true,false
true,false,true
false,false,true
""", false);
        assertEquals("Not (b)", outcome);
    }

    @Test
    void conflictingSource() {
        var runner = new BruteForceRunner();
        var outcome = runner.loadFromString("""
true,true,false
true,true,true
""", false);
        assertEquals("Conflicting pair. This pair is a duplicate: [true, true]", outcome);
    }

    @Test
    void useRegistersFromHeader() {
        var runner = new BruteForceRunner();
        var outcome = runner.loadFromString("""
k,l,o
true,true,false
true,false,true
false,false,true
""", true);
        assertEquals("Not (l)", outcome);
    }

    //Bad input.
    @Test
    void invalidInput() {
        var runner = new BruteForceRunner();
        var outcome = runner.loadFromString("""
true,true,potato
true,true,true
""", false);
        assertEquals("java.lang.IllegalArgumentException: Invalid value potato expecting true, false or 0, 1", outcome);
    }
    @Test
    void onlyFailures() {
        var runner = new BruteForceRunner();
        var outcome = runner.loadFromString("""
true,true,false
""", false);
        assertEquals("java.lang.IllegalArgumentException: No success records found. Just return false always.", outcome);
    }

    @Test
    void onlySuccesses() {
        var runner = new BruteForceRunner();
        var outcome = runner.loadFromString("""
true,true,true
""", false);
        assertEquals("java.lang.IllegalArgumentException: No fail records found. Just return true always.", outcome);
    }


}