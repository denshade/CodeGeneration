package laboflieven;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccProgramParserTest {

    @Test
    void parse() {
        String s = "[left = left + right, left = left + right,  right = R2, left = left + right, left = left + right, R2 = left,  right = R1, R2 = right, left = left / right, left++, R1 = right, R1 = left, left = left / right, left = left + right, R2 = left]";
        var k = AccProgramParser.parse(s);
        assertEquals(s,k.toString());
    }
}