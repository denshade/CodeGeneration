package laboflieven.humanresource.challenges;

import laboflieven.humanresource.BruteForceProgramIterator;
import laboflieven.humanresource.HumanInOutput;
import laboflieven.humanresource.HumanProgramFitnessExaminer;
import laboflieven.humanresource.model.HumanInstructionEnum;

import java.util.List;

//Found a program: [Inbox, CopyTo r0, Inbox, Outbox, CopyFrom r0, Outbox, Jump to 0]
public class Level7 {
    public static void main(String[] args)
    {
        var input = new HumanInOutput();
        input.input = List.of(2,0,3,0,0,6);
        input.output = List.of(2,3,6);
        var evaluator = new HumanProgramFitnessExaminer(List.of(input)
                ,100);
        var iterator = new BruteForceProgramIterator(evaluator, List.of(HumanInstructionEnum.INBOX, HumanInstructionEnum.OUTBOX, HumanInstructionEnum.JumpIfZero, HumanInstructionEnum.LOOP).toArray(new HumanInstructionEnum[0]));
        iterator.iterate(1, 4);
    }
}