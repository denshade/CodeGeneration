package laboflieven.recursionheuristics;

import laboflieven.ProgramParser;
import laboflieven.statements.Instruction;
import laboflieven.statements.Invert;
import laboflieven.statements.Register;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NoInvertedHeuristicTest {

    @Test
    void shouldRecurse() {
        NoInvertedHeuristic n = new NoInvertedHeuristic();
        List<Instruction> instructionList = new ArrayList<Instruction>();
        Register r1 = new Register("R1");
        Register r2 = new Register("R2");

        assertTrue(n.shouldRecurse(instructionList,1));
        instructionList.add(new Invert(r1));
        instructionList.add(new Invert(r1));
        assertFalse(n.shouldRecurse(instructionList,1));
        instructionList.clear();
        instructionList.add(new Invert(r1));
        instructionList.add(new Invert(r2));
        assertTrue(n.shouldRecurse(instructionList,2));
        assertFalse(n.shouldRecurse(ProgramParser.parse("[R1 += R1, Invert R1, Invert R1, Cos R1]"),1));
        assertFalse(n.shouldRecurse(ProgramParser.parse("[R1 += R1, Mul R1 -> R1, Sqrt R1, Invert R1, Cos R1]"), 1));
        assertFalse(n.shouldRecurse(ProgramParser.parse("[PI R1, Mul R1 -> R1, Sqrt R1, Invert R1, Cos R1]"), 1));
        assertFalse(n.shouldRecurse(ProgramParser.parse("[Move R1 -> R1, Invert R1, Cos R1]"), 1));
        assertFalse(n.shouldRecurse(ProgramParser.parse("[Invert R1, Move R1 -> R1]"), 1));


    }
}