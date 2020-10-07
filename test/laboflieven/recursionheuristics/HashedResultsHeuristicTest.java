package laboflieven.recursionheuristics;

import laboflieven.InstructionMark;
import laboflieven.Program;
import laboflieven.accinstructions.*;
import laboflieven.runners.AccStatementRunner;
import laboflieven.statements.Register;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class HashedResultsHeuristicTest {

    @Test
    void shouldRecurseFirstTime() {
        Map<String, Double> k = new HashMap<>();
        k.put("r1", 2.0);
        k.put("r2", 2.0);
        k.put("r3", 2.0);
        k.put("r4", 2.0);
        List<InstructionMark> instructs = new ArrayList<>();
        Program p = new Program(instructs, Register.create4Registers());

        HashedResultsHeuristic h = new HashedResultsHeuristic(k, new AccStatementRunner(2));
        assertTrue(h.shouldRecurse(p,2));

    }

    @Test
    void shouldNotRecurseIfSmallerProgramExists() {
        Map<String, Double> k = new HashMap<>();
        k.put("r1", 2.0);
        k.put("r2", 2.0);
        k.put("r3", 2.0);
        k.put("r4", 2.0);
        List<InstructionMark> instructs = new ArrayList<>();
        instructs.add(new Add()); //L + R = 0
        Program p = new Program(instructs, Register.create4Registers());
        HashedResultsHeuristic h = new HashedResultsHeuristic(k, new AccStatementRunner(2));
        assertTrue(h.shouldRecurse(p,2));
        instructs.add(new Add()); //L + R = 0
        assertFalse(h.shouldRecurse(p,2));
    }

    @Test
    void shouldNotRecurseIfSmallerProgramExistsCaseStudy() {
        Map<String, Double> k = new HashMap<>();
        k.put("r1", 2.0);
        k.put("r2", 2.0);
        k.put("r3", 2.0);
        k.put("r4", 2.0);
        List<Register> registerList = Register.create4Registers();
        List<InstructionMark> instructs = new ArrayList<>();
        instructs.add(new AccLeftPush(registerList.get(0)));
        instructs.add(new AccRightPush(registerList.get(0)));
        instructs.add(new Add());
        Program p = new Program(instructs, Register.create4Registers());
        HashedResultsHeuristic h = new HashedResultsHeuristic(k, new AccStatementRunner(100));
        assertTrue(h.shouldRecurse(p,2));
        List<InstructionMark> instructs2 = new ArrayList<>();
        instructs2.add(new AccRightPush(registerList.get(0)));
        instructs2.add(new AccLeftPush(registerList.get(0)));
        instructs2.add(new Add());
        Program p2 = new Program(instructs2, Register.create4Registers());
        assertFalse(h.shouldRecurse(p2,2));
    }

}