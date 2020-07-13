import laboflieven.ProgramEnumerator;
import laboflieven.statements.*;
import laboflieven.statements.Register;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProgramEnumeratorTest {

    @Test
    void convert() {
        Register r1 = new Register("R1");
        Register r2 = new Register("R2");

        ProgramEnumerator enumator = new ProgramEnumerator(List.of(new Add(r1, r2), new Cos(r1), new Mul(r1, r2)), 2);
        var l = enumator.convert(Arrays.asList(new Add(r1, r2), new Cos(r1), new Mul(r1, r2)));//0+1*13+13*13*8
        assertEquals(l , 741);
        l = enumator.convert(Arrays.asList(new Add(r1, r1), new Cos(r1), new Mul(r1, r2)));//0+1*13+13*13*8
        assertEquals(l , 740);

    }
}