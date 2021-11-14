package laboflieven.accinstructions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AccInstructionOpcodeEnumTest {

    @Test
    void isSingleRegister() {
        assertTrue(AccInstructionOpcodeEnum.LoadAccLeftIntoRegister.isSingleRegister());
        assertFalse(AccInstructionOpcodeEnum.Add.isSingleRegister());

    }
}