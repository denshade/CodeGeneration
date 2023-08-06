package laboflieven.recursionheuristics;

import laboflieven.InstructionMark;
import laboflieven.Program;
import laboflieven.ProgramParser;
import laboflieven.registers.NumberNamingScheme;
import laboflieven.statements.Invert;
import laboflieven.registers.Register;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NoInvertedHeuristicTest {

    @Test
    void shouldRecurse() {
        NoInvertedHeuristic n = new NoInvertedHeuristic();
        List<InstructionMark> instructionList = new ArrayList<InstructionMark>();
        Register r1 = new Register("R1");
        Register r2 = new Register("R2");
        Program p = new Program(instructionList, new NumberNamingScheme().createRegisters(2));

        assertTrue(n.shouldRecurse(p,1));
        instructionList.add(new Invert(r1));
        instructionList.add(new Invert(r1));
        assertFalse(n.shouldRecurse(p,1));
        instructionList.clear();
        instructionList.add(new Invert(r1));
        instructionList.add(new Invert(r2));
        assertTrue(n.shouldRecurse(p,2));
        List<Register> r = new NumberNamingScheme().createRegisters(1);
        assertFalse(n.shouldRecurse(new Program(ProgramParser.parse("[R1 += R1, Invert R1, Invert R1, Cos R1]"), r), 1));
        assertFalse(n.shouldRecurse(new Program(ProgramParser.parse("[R1 += R1, Mul R1 -> R1, Sqrt R1, Invert R1, Cos R1]"), r), 1));
        assertFalse(n.shouldRecurse(new Program(ProgramParser.parse("[PI R1, Mul R1 -> R1, Sqrt R1, Invert R1, Cos R1]"), r), 1));
        assertFalse(n.shouldRecurse(new Program(ProgramParser.parse("[Move R1 -> R1, Invert R1, Cos R1]"), r), 1));
        assertFalse(n.shouldRecurse(new Program(ProgramParser.parse("[Invert R1, Move R1 -> R1]"), r), 1));
    }
}