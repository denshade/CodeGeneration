package laboflieven.statements;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.Set;

class RegularInstructionOpcodeTest {

    @Test
    void anyExcept() {
        Assert.assertFalse(Set.of(RegularInstructionOpcode.anyExcept(Set.of(RegularInstructionOpcode.JmpIfNotZeroBegin))).contains(RegularInstructionOpcode.JmpIfNotZeroBegin));
        Assert.assertEquals(RegularInstructionOpcode.anyExcept(Set.of(RegularInstructionOpcode.JmpIfNotZeroBegin)).length, RegularInstructionOpcode.values().length - 1);
        Assert.assertEquals(RegularInstructionOpcode.anyExcept(RegularInstructionOpcode.JmpIfNotZeroBegin).length, RegularInstructionOpcode.values().length - 1);

    }
}