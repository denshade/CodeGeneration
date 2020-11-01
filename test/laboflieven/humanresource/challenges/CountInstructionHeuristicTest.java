package laboflieven.humanresource.challenges;

import laboflieven.humanresource.instructions.Add;
import laboflieven.humanresource.instructions.Jump;
import laboflieven.humanresource.model.HumanRegister;
import laboflieven.statements.Register;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CountInstructionHeuristicTest {

    @Test
    public void test()
    {
        var map = new HashMap<Class, Integer>();
        map.put(Add.class,1);
        var counter = new CountInstructionHeuristic(map);
        assertTrue(counter.shouldRecurse(List.of(new Add(new HumanRegister("r1")))));
        assertFalse(counter.shouldRecurse(List.of(new Add(new HumanRegister("r1")), new Add(new HumanRegister("r1")))));

    }

    @Test
    public void testMultipleCounts()
    {
        var map = new HashMap<Class, Integer>();
        map.put(Add.class,1);
        map.put(Jump.class,1);

        var counter = new CountInstructionHeuristic(map);
        assertTrue(counter.shouldRecurse(List.of(new Add(new HumanRegister("r1")))));
        assertTrue(counter.shouldRecurse(List.of(new Jump(0))));
        assertTrue(counter.shouldRecurse(List.of(new Jump(0))));

    }

}