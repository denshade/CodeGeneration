package laboflieven.functional.loggers;

import laboflieven.accinstructions.AccInstructionOpcodeEnum;
import laboflieven.accinstructions.Add;
import laboflieven.common.AccInstructionOpcode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InstructionIndexPairTest {

    @Test
    void pairUsesFilteredOpcodes()
    {
        InstructionIndexPair pair = new InstructionIndexPair(List.of(new Add(), new Add()), 2, List.of(new AccInstructionOpcode(AccInstructionOpcodeEnum.Add)));
        assertTrue(pair.getX().intValue() < 4);
    }

}