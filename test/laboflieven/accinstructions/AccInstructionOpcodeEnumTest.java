package laboflieven.accinstructions;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

class AccInstructionOpcodeEnumTest {

    @Test
    void isSingleRegister() {
        Assert.assertTrue(AccInstructionOpcodeEnum.LoadAccLeftIntoRegister.isSingleRegister());
        Assert.assertFalse(AccInstructionOpcodeEnum.Add.isSingleRegister());

    }
}