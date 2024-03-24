package laboflieven.instructions.logic;

import laboflieven.registers.TemplateRegister;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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

    //use the source to name the variables.

}