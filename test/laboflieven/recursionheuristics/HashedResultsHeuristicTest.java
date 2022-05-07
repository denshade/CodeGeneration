package laboflieven.recursionheuristics;

import laboflieven.TestcaseInOutParameters;
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
        List<InstructionMark> instructs = new ArrayList<>();
        Program p = new Program(instructs, Register.createRegisters(2));

        HashedResultsHeuristic h = new HashedResultsHeuristic( getExampleParameters(), new AccStatementRunner(2));
        assertTrue(h.shouldRecurse(p,2));

    }

    @Test
    void shouldNotRecurseIfSmallerProgramExists() {
        List<TestcaseInOutParameters> solutions = getExampleParameters();
        List<InstructionMark> instructs = new ArrayList<>();
        instructs.add(new Add()); //L + R = 0
        Program p = new Program(instructs, Register.createRegisters(2));
        HashedResultsHeuristic h = new HashedResultsHeuristic(solutions, new AccStatementRunner(2));
        assertTrue(h.shouldRecurse(p,2));
        instructs.add(new Add()); //L + R = 0
        assertFalse(h.shouldRecurse(p,2));
    }

    private List<TestcaseInOutParameters> getExampleParameters() {
        TestcaseInOutParameters param = getInOutParameter(2);
        TestcaseInOutParameters param2 = getInOutParameter(3);
        return List.of(param, param2);
    }

    private TestcaseInOutParameters getInOutParameter(double val) {
        Map<String, Double> k = new HashMap<>();
        k.put("r1", val);
        k.put("r2", val + 1);
        TestcaseInOutParameters param = new TestcaseInOutParameters();
        param.input = k;
        return param;
    }

    @Test
    void shouldNotRecurseIfSmallerProgramExistsCaseStudy() {
        List<Register> registerList = Register.createRegisters(2);
        List<InstructionMark> instructs = new ArrayList<>();
        instructs.add(new LoadIntoLeftAcc(registerList.get(0)));
        instructs.add(new LoadIntoRightAcc(registerList.get(0)));
        instructs.add(new Add());
        Program p = new Program(instructs, registerList);
        HashedResultsHeuristic h = new HashedResultsHeuristic( getExampleParameters(), new AccStatementRunner(100));
        assertTrue(h.shouldRecurse(p,2));
        List<InstructionMark> instructs2 = new ArrayList<>();
        instructs2.add(new LoadIntoRightAcc(registerList.get(0)));
        instructs2.add(new LoadIntoLeftAcc(registerList.get(0)));
        instructs2.add(new Add());
        Program p2 = new Program(instructs2, registerList);
        assertFalse(h.shouldRecurse(p2,2));
    }

}