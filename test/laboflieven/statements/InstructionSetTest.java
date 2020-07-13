package laboflieven.statements;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.Set;

class InstructionSetTest {

    @Test
    void anyExcept() {
        Assert.assertFalse(Set.of(InstructionSet.anyExcept(Set.of(InstructionSet.JmpIfNotZeroBegin))).contains(InstructionSet.JmpIfNotZeroBegin));
        Assert.assertEquals(InstructionSet.anyExcept(Set.of(InstructionSet.JmpIfNotZeroBegin)).length, InstructionSet.values().length - 1);
        Assert.assertEquals(InstructionSet.anyExcept(InstructionSet.JmpIfNotZeroBegin).length, InstructionSet.values().length - 1);

    }
}