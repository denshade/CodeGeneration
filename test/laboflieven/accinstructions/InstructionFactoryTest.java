package laboflieven.accinstructions;

import laboflieven.instructions.accinstructions.AccInstructionOpcodeEnum;
import laboflieven.instructions.accinstructions.InstructionFactory;
import laboflieven.registers.Register;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InstructionFactoryTest {

    @Test
    void createInstruction() {
        InstructionFactory fact = new InstructionFactory();
        assertEquals("laboflieven.accinstructions.Pow", fact.createInstruction(new laboflieven.common.AccInstructionOpcode(AccInstructionOpcodeEnum.Pow)).getClass().getCanonicalName());
        assertEquals("left = R1", fact.createInstructionP(AccInstructionOpcodeEnum.LoadIntoLeftAcc, new Register("R1")).toString().trim());
    }

    @Test
    void timeInstruction() {
        InstructionFactory fact = new InstructionFactory();
        long l = System.currentTimeMillis();
        for (int i = 0; i < 1000000;i++)
        {
            fact.createInstruction(new laboflieven.common.AccInstructionOpcode(AccInstructionOpcodeEnum.Pow)).getClass().getCanonicalName();
        }
        System.out.println(System.currentTimeMillis() - l);
    }

    @Test
    void tryAllInstructionEnums()
    {
        InstructionFactory fact = new InstructionFactory();
        for (AccInstructionOpcodeEnum enums : AccInstructionOpcodeEnum.values())
        {
            if (enums.isSingleRegister())
            {
                assertNotNull(fact.createInstructionP(enums, new Register("R1")));
            } else {
                assertNotNull(fact.createInstructionP(enums));
            }
        }
    }
    @Test
    void generateRandom()
    {
        InstructionFactory fact = new InstructionFactory();
        assertNotNull(fact.generateRandomInstruction(List.of(new Register("R1"))));
    }
}