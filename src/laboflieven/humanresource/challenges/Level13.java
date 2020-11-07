package laboflieven.humanresource.challenges;

import laboflieven.humanresource.BruteForceProgramIterator;
import laboflieven.humanresource.HumanInOutput;
import laboflieven.humanresource.HumanProgramFitnessExaminer;
import laboflieven.humanresource.heuristics.CountInstructionHeuristic;
import laboflieven.humanresource.heuristics.StartInboxOutboxLoopCountInstructionHeuristic;
import laboflieven.humanresource.instructions.*;
import laboflieven.humanresource.model.HumanInstructionEnum;

import java.util.HashMap;
import java.util.List;

//Found a program: [Inbox, CopyTo r0, Inbox, Outbox, CopyFrom r0, Outbox, Jump to 0]
public class Level13 {
    public static void main(String[] args)
    {
        int maxNr = 9;
        var input = new HumanInOutput();
        input.input = List.of(5,6, 6,6, -4,9, 5,5, 1,1, 3,2, 4,5, 6,7, 8,9);
        input.output = List.of(6,5,1);

        var map = new HashMap<Class, Integer>();
        map.put(Inbox.class, 2);
        map.put(Sub.class, 1);
        map.put(Jump.class,2);
        map.put(JumpIfZero.class,1);
        map.put(CopyFrom.class,1);
        map.put(CopyTo.class,1);
        map.put(Outbox.class, 1);
        var heuristic = new CountInstructionHeuristic(map);


        var evaluator = new HumanProgramFitnessExaminer(List.of(input)
                ,300);
        var iterator = new BruteForceProgramIterator(evaluator,
                List.of(
                        HumanInstructionEnum.INBOX,
                        HumanInstructionEnum.OUTBOX,
                        HumanInstructionEnum.JumpIfZero,
                        HumanInstructionEnum.LOOP,
                        HumanInstructionEnum.CopyTo,
                        HumanInstructionEnum.CopyFrom,
                        HumanInstructionEnum.Sub
                ).toArray(new HumanInstructionEnum[0]),
                heuristic);
        iterator.iterate(1, maxNr);
    }
}
