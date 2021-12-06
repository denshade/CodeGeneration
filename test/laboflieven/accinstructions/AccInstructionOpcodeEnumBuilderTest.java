package laboflieven.accinstructions;

import laboflieven.common.AccInstructionOpcode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccInstructionOpcodeEnumBuilderTest
{

    @Test
    void checkBuilderWithEnumNames()
    {
        AccInstructionOpcodeEnumBuilder builder = AccInstructionOpcodeEnumBuilder.make();
        assertEquals(AccInstructionOpcodeEnum.Inc, builder.fromString("Inc,Dec").build()[0]);
        assertEquals(AccInstructionOpcodeEnum.Dec, builder.fromString("Inc,Dec").build()[1]);
    }

}