package laboflieven.regular;

import laboflieven.registers.Register;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegisterTest {

    @Test
    void createAlphabetRegisters() {
        assertEquals("a", Register.createAlphabetRegisters(1).get(0).name);
        assertEquals("a", Register.getName(1));
    }
}