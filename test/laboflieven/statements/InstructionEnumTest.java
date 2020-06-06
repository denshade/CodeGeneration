package laboflieven.statements;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class InstructionEnumTest {

    @Test
    void anyExcept() {
        Assert.assertFalse(Set.of(InstructionEnum.anyExcept(Set.of(InstructionEnum.JmpIfNotZeroBegin))).contains(InstructionEnum.JmpIfNotZeroBegin));
        Assert.assertEquals(InstructionEnum.anyExcept(Set.of(InstructionEnum.JmpIfNotZeroBegin)).length, InstructionEnum.values().length - 1);
        Assert.assertEquals(InstructionEnum.anyExcept(InstructionEnum.JmpIfNotZeroBegin).length, InstructionEnum.values().length - 1);

    }
}