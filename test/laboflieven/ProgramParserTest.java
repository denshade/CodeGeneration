package laboflieven;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProgramParserTest {

    @Test
    void parse() {
        assertEquals(10, ProgramParser.parse("[Cos r3, Mul r1 -> r1, r2 -= r1, r0 /= r2, r3 += r0, r3 += r0, r3 += r0, r3 += r0, r3 += r0, r3 += r0]").size());

    }
}