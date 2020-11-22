package laboflieven.humanresource.challenges;

import laboflieven.humanresource.BruteForceProgramIterator;
import laboflieven.humanresource.HumanInOutput;
import laboflieven.humanresource.HumanProgramFitnessExaminer;
import laboflieven.humanresource.model.HumanInstructionSet;

import java.util.List;

public class Level2 {
    public static void main(String[] args)
    {
        var input = new HumanInOutput();
        input.input = List.of(8,4,7);
        input.output = List.of(8,4,7);
        var evaluator = new HumanProgramFitnessExaminer(List.of(input)
                ,100);
        var iterator = new BruteForceProgramIterator(evaluator, List.of(HumanInstructionSet.INBOX, HumanInstructionSet.OUTBOX, HumanInstructionSet.LOOP ).toArray(new HumanInstructionSet[0]));
        iterator.iterate(1, 3);
    }
}
