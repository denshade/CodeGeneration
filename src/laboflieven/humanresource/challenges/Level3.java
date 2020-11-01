package laboflieven.humanresource.challenges;

import laboflieven.humanresource.BruteForceProgramIterator;
import laboflieven.humanresource.HumanInOutput;
import laboflieven.humanresource.HumanProgramFitnessExaminer;
import laboflieven.humanresource.model.HumanInstructionEnum;

import java.util.List;

public class Level3 {
    public static void main(String[] args)
    {
        var input = new HumanInOutput();
        input.input = List.of();
        input.output = List.of(8,4,7);
        var evaluator = new HumanProgramFitnessExaminer(List.of(input)
                ,6);
        var iterator = new BruteForceProgramIterator(evaluator, List.of(HumanInstructionEnum.INBOX, HumanInstructionEnum.OUTBOX, HumanInstructionEnum.LOOP ).toArray(new HumanInstructionEnum[0]));
        iterator.iterate(5, 3);
    }
}