package laboflieven.humanresource.challenges;

import laboflieven.humanresource.BruteForceProgramIterator;
import laboflieven.humanresource.HumanInOutput;
import laboflieven.humanresource.HumanProgramFitnessExaminer;
import laboflieven.humanresource.model.HumanInstructionSet;

import java.util.List;

//Found a program: [Inbox, CopyTo r0, Inbox, Outbox, CopyFrom r0, Outbox, Jump to 0]
public class Level4 {
    public static void main(String[] args)
    {
        var input = new HumanInOutput();
        input.input = List.of(2,9,-5,-6,1,6);
        input.output = List.of(9,2,-6,-5,6,1);
        var evaluator = new HumanProgramFitnessExaminer(List.of(input)
                ,100);
        var iterator = new BruteForceProgramIterator(evaluator, List.of(HumanInstructionSet.INBOX, HumanInstructionSet.OUTBOX, HumanInstructionSet.CopyTo, HumanInstructionSet.CopyFrom, HumanInstructionSet.LOOP).toArray(new HumanInstructionSet[0]));
        iterator.iterate(1, 7);
    }
}
