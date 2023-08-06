package laboflieven.functional.loggers;

import laboflieven.accinstructions.AccInstructionOpcodeEnum;
import laboflieven.accinstructions.Add;
import laboflieven.accinstructions.LoadIntoLeftAcc;
import laboflieven.common.AccInstructionOpcode;
import laboflieven.registers.Register;
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

    @Test
    void conflict()
    {
        var pair = new InstructionIndexPair(List.of(new LoadIntoLeftAcc(new Register("R1"))), 2, List.of(new AccInstructionOpcode(AccInstructionOpcodeEnum.LoadIntoLeftAcc)));
        var pair2 = new InstructionIndexPair(List.of(new LoadIntoLeftAcc(new Register("R2"))), 2, List.of(new AccInstructionOpcode(AccInstructionOpcodeEnum.LoadIntoLeftAcc)));
        assertNotEquals(pair.getY(), pair2.getY());
    }

}