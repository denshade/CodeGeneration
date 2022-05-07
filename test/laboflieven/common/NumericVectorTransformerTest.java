package laboflieven.common;

import laboflieven.InstructionMark;
import laboflieven.accinstructions.AccInstructionOpcodeEnum;
import laboflieven.accinstructions.Add;
import laboflieven.accinstructions.Sub;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NumericVectorTransformerTest {

    @Test
    void testProgramToNumericVectorTransform()
    {
        NumericVectorTransformer transformer = new NumericVectorTransformer();
        List<Double> actual = transformer.programToNumericList(List.of(new Add()), List.of(new AccInstructionOpcode(AccInstructionOpcodeEnum.Add)));
        assertNotNull(actual);
        assertEquals(1, actual.size());
        assertEquals(0.0, actual.get(0));
    }

    @Test
    void testNumericVectorToProgramTransform()
    {
        NumericVectorTransformer transformer = new NumericVectorTransformer();
        List<InstructionMark> actual = transformer.numericListToProgram(List.of(0.0), List.of(new AccInstructionOpcode(AccInstructionOpcodeEnum.Add)));
        assertNotNull(actual);
        assertEquals(1, actual.size());
        assertEquals(new Add(), actual.get(0));
    }

    @Test
    void unknownOpcodeThrows()
    {
        NumericVectorTransformer transformer = new NumericVectorTransformer();
        assertThrows(RuntimeException.class, () -> transformer.programToNumericList(List.of(new Sub()), List.of(new AccInstructionOpcode(AccInstructionOpcodeEnum.Add))));
    }

}