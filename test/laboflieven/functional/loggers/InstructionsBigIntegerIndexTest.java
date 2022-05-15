package laboflieven.functional.loggers;

import laboflieven.accinstructions.AccInstructionOpcodeEnum;
import laboflieven.accinstructions.Add;
import laboflieven.accinstructions.LoadIntoLeftAcc;
import laboflieven.common.AccInstructionOpcode;
import laboflieven.statements.Register;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InstructionsBigIntegerIndexTest {

    @Test
    void testBigInteger()
    {
        var v = new InstructionsBigIntegerIndex(List.of(new LoadIntoLeftAcc(new Register("R1"))), List.of(new AccInstructionOpcode(AccInstructionOpcodeEnum.LoadIntoLeftAcc)));
        assertEquals(0, v.getSumRegister().intValue());
        var v2 = new InstructionsBigIntegerIndex(List.of(new Add()), List.of(new AccInstructionOpcode(AccInstructionOpcodeEnum.LoadIntoLeftAcc), new AccInstructionOpcode(AccInstructionOpcodeEnum.Add)));
        assertEquals(1, v2.getSumRegister().intValue());
        var v3 = new InstructionsBigIntegerIndex(List.of(new Add(), new Add()), List.of(new AccInstructionOpcode(AccInstructionOpcodeEnum.LoadIntoLeftAcc), new AccInstructionOpcode(AccInstructionOpcodeEnum.Add)));
        assertEquals(4, v3.getSumRegister().intValue());
        var v4 = new InstructionsBigIntegerIndex(List.of(new LoadIntoLeftAcc(new Register("R1")), new LoadIntoLeftAcc(new Register("R1"))), List.of(new AccInstructionOpcode(AccInstructionOpcodeEnum.LoadIntoLeftAcc), new AccInstructionOpcode(AccInstructionOpcodeEnum.Add)));
        assertEquals(0, v4.getSumRegister().intValue());
        var v5 = new InstructionsBigIntegerIndex(List.of(new Add(), new LoadIntoLeftAcc(new Register("R1"))), List.of(new AccInstructionOpcode(AccInstructionOpcodeEnum.LoadIntoLeftAcc), new AccInstructionOpcode(AccInstructionOpcodeEnum.Add)));
        assertEquals(1, v5.getSumRegister().intValue());
    }

}