package laboflieven.accinstructions;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

class InstructionFactoryTest {

    @Test
    void createInstruction() {
        assertEquals("laboflieven.accinstructions.Pow", InstructionFactory.createInstruction(InstructionEnum.Pow).getClass().getCanonicalName());
    }

    @Test
    void timeInstruction() throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException {
        long l = System.currentTimeMillis();
        for (int i = 0; i < 1000000;i++)
        {
            InstructionFactory.createInstruction(InstructionEnum.Pow).getClass().getCanonicalName();
        }
        System.out.println(System.currentTimeMillis() - l);
    }
}