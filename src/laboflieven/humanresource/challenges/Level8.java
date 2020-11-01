package laboflieven.humanresource.challenges;

import laboflieven.humanresource.BruteForceProgramIterator;
import laboflieven.humanresource.HumanInOutput;
import laboflieven.humanresource.HumanProgramFitnessExaminer;
import laboflieven.humanresource.model.HumanInstructionEnum;

import java.util.List;

//Found a program: [Inbox, CopyTo r0, Inbox, Outbox, CopyFrom r0, Outbox, Jump to 0]
public class Level8 {
    public static void main(String[] args)
    {
        var input = new HumanInOutput();
        input.input = List.of(5,-2,7,0);
        input.output = List.of(5*3,-2*3,7*3,0);
        var evaluator = new HumanProgramFitnessExaminer(List.of(input)
                ,6);
        var iterator = new BruteForceProgramIterator(evaluator, List.of(HumanInstructionEnum.INBOX, HumanInstructionEnum.OUTBOX, HumanInstructionEnum.CopyTo, HumanInstructionEnum.CopyFrom, HumanInstructionEnum.LOOP, HumanInstructionEnum.ADD).toArray(new HumanInstructionEnum[0]));
        iterator.iterate(1, 6);
    }
}
