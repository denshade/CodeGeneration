package laboflieven.humanresource.challenges;

import laboflieven.humanresource.BruteForceProgramIterator;
import laboflieven.humanresource.HumanInOutput;
import laboflieven.humanresource.HumanProgramFitnessExaminer;
import laboflieven.humanresource.heuristics.CountInstructionHeuristic;
import laboflieven.humanresource.instructions.*;
import laboflieven.humanresource.model.HumanInstructionEnum;

import java.util.HashMap;
import java.util.List;

public class Level17 {
    public static void main(String[] args)
    {
        int maxNr = 9;
        var input = new HumanInOutput();
        input.input = List.of(-1,-1,1,-3,6,4,-4,5);
        input.output = List.of(0,1,0,1);

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
                        HumanInstructionEnum.JumpIfNegative,
                        HumanInstructionEnum.LOOP,
                        HumanInstructionEnum.CopyTo,
                        HumanInstructionEnum.CopyFrom,
                        HumanInstructionEnum.Sub,
                        HumanInstructionEnum.ADD
                ).toArray(new HumanInstructionEnum[0]),
                heuristic);
        iterator.iterate(3, maxNr);
    }
}
