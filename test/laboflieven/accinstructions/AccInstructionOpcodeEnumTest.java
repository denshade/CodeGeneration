package laboflieven.accinstructions;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccInstructionOpcodeEnumTest {

    @Test
    void isSingleRegister() {
        Assert.assertTrue(AccInstructionOpcodeEnum.AccLeftPull.isSingleRegister());
        Assert.assertFalse(AccInstructionOpcodeEnum.Add.isSingleRegister());

    }
}