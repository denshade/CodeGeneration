package laboflieven.humanresource.challenges;

import laboflieven.humanresource.BruteForceProgramIterator;
import laboflieven.humanresource.HumanInOutput;
import laboflieven.humanresource.HumanProgramFitnessExaminer;
import laboflieven.humanresource.heuristics.CountInstructionHeuristic;
import laboflieven.humanresource.instructions.*;
import laboflieven.humanresource.model.HumanInstructionSet;
import laboflieven.humanresource.model.HumanRegister;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//Found a program: [Inbox, CopyTo r0, Add, CopyTo r0, Add, CopyTo r0, Add, Outbox, Jump to 0]
public class Level10 {
    public static void main(String[] args)
    {
        var input = new HumanInOutput();
        input.input = List.of(5,-2,7,0);
        input.output = List.of(5*8,-2*8,7*8,0);
        var evaluator = new HumanProgramFitnessExaminer(List.of(input)
                ,100);
        var map = new HashMap<Class<?>, Integer>();
        map.put(Inbox.class, 1);
        map.put(CopyTo.class, 3);
        map.put(Add.class, 3);
        map.put(Outbox.class, 1);
        map.put(Jump.class, 1);


        var heuristic = new CountInstructionHeuristic(map);
        var iterator = new BruteForceProgramIterator(evaluator,
                List.of(HumanInstructionSet.INBOX, HumanInstructionSet.OUTBOX, HumanInstructionSet.CopyTo, HumanInstructionSet.JUMP, HumanInstructionSet.ADD).toArray(new HumanInstructionSet[0]),
                heuristic);
        iterator.iterate(1, 9);
        List<HumanRegister> registers = new ArrayList<>();
        HumanRegister r0 = new HumanRegister("r0");
        registers.add(r0);

        System.out.println(iterator.isValid(List.of(new Inbox(), new CopyTo(r0), new Add(r0), new CopyTo(r0), new Add(r0), new CopyTo(r0), new Add(r0), new Outbox(), new Jump(0)),registers));
        iterator.eval(List.of(new Inbox(), new CopyTo(r0), new Add(r0), new CopyTo(r0), new Add(r0), new CopyTo(r0), new Add(r0), new Outbox(), new Jump(0)),registers);
    }
}
