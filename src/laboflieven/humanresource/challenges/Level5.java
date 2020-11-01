package laboflieven.humanresource.challenges;

import laboflieven.humanresource.BruteForceProgramIterator;
import laboflieven.humanresource.HumanInOutput;
import laboflieven.humanresource.HumanProgramFitnessExaminer;
import laboflieven.humanresource.model.HumanInstructionEnum;

import java.util.List;

//Found a program: [Inbox, CopyTo r0, Inbox, Outbox, CopyFrom r0, Outbox, Jump to 0]
public class Level5 {
    public static void main(String[] args)
    {
        var input = new HumanInOutput();
        input.input = List.of(2,2,3,4,-3,6);
        input.output = List.of(4,7,3);
        var evaluator = new HumanProgramFitnessExaminer(List.of(input)
                ,6);
        var iterator = new BruteForceProgramIterator(evaluator, List.of(HumanInstructionEnum.INBOX, HumanInstructionEnum.OUTBOX, HumanInstructionEnum.CopyTo, HumanInstructionEnum.CopyFrom, HumanInstructionEnum.LOOP, HumanInstructionEnum.ADD).toArray(new HumanInstructionEnum[0]));
        iterator.iterate(1, 6);
    }
}