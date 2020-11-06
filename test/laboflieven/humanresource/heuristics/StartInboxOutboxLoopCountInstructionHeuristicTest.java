package laboflieven.humanresource.heuristics;

import laboflieven.humanresource.instructions.*;
import laboflieven.humanresource.model.HumanInstruction;
import laboflieven.humanresource.model.HumanRegister;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StartInboxOutboxLoopCountInstructionHeuristicTest {

    @Test
    public void testA()
    {
        var map = new HashMap<Class, Integer>();
        map.put(Add.class,1);
        var counter = new StartInboxOutboxLoopCountInstructionHeuristic(map, 16);
        assertFalse(counter.shouldRecurse(List.of(new Add(new HumanRegister("r1")))));
        assertTrue(counter.shouldRecurse(List.of(new Inbox())));
        assertFalse(counter.shouldRecurse(List.of(new Inbox(), new Add(null), new Jump(0))));
        assertFalse(counter.shouldRecurse(List.of(new Inbox(), new Add(null), new Outbox())));
        var maxNr = 16;
        map = new HashMap<Class, Integer>();
        map.put(Inbox.class, 1);
        map.put(CopyTo.class, maxNr-3);
        map.put(Add.class, maxNr-3);
        map.put(Outbox.class, 1);
        map.put(Jump.class, 1);
        counter = new StartInboxOutboxLoopCountInstructionHeuristic(map, 16);
        var r0 = new HumanRegister("r0");
        var r1 = new HumanRegister("r1");
        var l = new ArrayList<HumanInstruction>();
        l.add(new Inbox()); // 1
        l.add(new CopyTo(r0)); // 2
        l.add(new Add(r0)); //3
        l.add(new CopyTo(r0)); //4
        l.add(new Add(r0)); //5
        l.add(new CopyTo(r0)); //6
        l.add(new Add(r0)); //7
        l.add(new CopyTo(r1)); //8
        l.add(new CopyTo(r0)); //9
        l.add(new Add(r0)); //10
        l.add(new CopyTo(r0)); //11
        l.add(new Add(r0)); //12
        l.add(new CopyTo(r0)); //13
        l.add(new Add(r0)); //14
        l.add(new Outbox()); //15
        l.add(new Jump(0)); //16
        System.out.println(l);
        assertTrue(counter.shouldRecurse(l));

    }
}