package laboflieven.statements;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.Set;

class RegularInstructionOpcodeEnumTest {

    @Test
    void anyExcept() {
        Assert.assertFalse(Set.of(RegularInstructionOpcodeEnum.anyExcept(Set.of(RegularInstructionOpcodeEnum.JmpIfNotZeroBegin))).contains(RegularInstructionOpcodeEnum.JmpIfNotZeroBegin));
        Assert.assertEquals(RegularInstructionOpcodeEnum.anyExcept(Set.of(RegularInstructionOpcodeEnum.JmpIfNotZeroBegin)).length, RegularInstructionOpcodeEnum.values().length - 1);
        Assert.assertEquals(RegularInstructionOpcodeEnum.anyExcept(RegularInstructionOpcodeEnum.JmpIfNotZeroBegin).length, RegularInstructionOpcodeEnum.values().length - 1);

    }

    @Test
    void nrRegisters()
    {
        Assert.assertTrue(RegularInstructionOpcodeEnum.Move.isDualRegister());
        Assert.assertFalse(RegularInstructionOpcodeEnum.Sqrt.isDualRegister());
    }
}