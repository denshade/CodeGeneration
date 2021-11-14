package laboflieven.statements;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RegularInstructionOpcodeEnumTest {

    @Test
    void anyExcept() {
        assertFalse(Set.of(RegularInstructionOpcodeEnum.anyExcept(Set.of(RegularInstructionOpcodeEnum.JmpIfNotZeroBegin))).contains(RegularInstructionOpcodeEnum.JmpIfNotZeroBegin));
        assertEquals(RegularInstructionOpcodeEnum.anyExcept(Set.of(RegularInstructionOpcodeEnum.JmpIfNotZeroBegin)).length, RegularInstructionOpcodeEnum.values().length - 1);
        assertEquals(RegularInstructionOpcodeEnum.anyExcept(RegularInstructionOpcodeEnum.JmpIfNotZeroBegin).length, RegularInstructionOpcodeEnum.values().length - 1);

    }

    @Test
    void nrRegisters()
    {
        assertTrue(RegularInstructionOpcodeEnum.Move.isDualRegister());
        assertFalse(RegularInstructionOpcodeEnum.Sqrt.isDualRegister());
    }
}