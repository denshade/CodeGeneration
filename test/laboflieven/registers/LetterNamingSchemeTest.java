package laboflieven.registers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LetterNamingSchemeTest
{
    @Test
    void checkNaming()
    {
        var l = new LetterNamingScheme();
        assertEquals("a", l.getName(1));
    }

}