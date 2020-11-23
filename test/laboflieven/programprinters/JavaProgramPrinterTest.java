package laboflieven.programprinters;

import laboflieven.InstructionMark;
import laboflieven.Program;
import laboflieven.accinstructions.LoadAccLeftIntoRegister;
import laboflieven.statements.Register;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JavaProgramPrinterTest {

    @Test
    public void testJavaPrinter()
    {
        Register r1 = new Register("R1");
        List<InstructionMark> instructs = new ArrayList<>();
        instructs.add(new LoadAccLeftIntoRegister(r1));
        Program p = new Program(instructs, List.of(r1));
        var programPrinter = new JavaProgramPrinter();
        assertEquals("double run( double R1) {\nR1 = left;\n}", programPrinter.toProgram(p));
    }

}