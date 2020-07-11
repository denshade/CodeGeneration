package laboflieven.accinstructions;

import laboflieven.common.AccInstructionSet;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

class InstructionFactoryTest {

    @Test
    void createInstruction() {
        InstructionFactory fact = new InstructionFactory();
        assertEquals("laboflieven.accinstructions.Pow", fact.createInstruction(new AccInstructionSet(InstructionEnum.Pow)).getClass().getCanonicalName());
    }

    @Test
    void timeInstruction() throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException {
        InstructionFactory fact = new InstructionFactory();
        long l = System.currentTimeMillis();
        for (int i = 0; i < 1000000;i++)
        {
            fact.createInstruction(new AccInstructionSet(InstructionEnum.Pow)).getClass().getCanonicalName();
        }
        System.out.println(System.currentTimeMillis() - l);
    }
}