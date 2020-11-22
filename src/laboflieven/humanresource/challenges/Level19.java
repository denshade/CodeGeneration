package laboflieven.humanresource.challenges;

import laboflieven.common.Configuration;
import laboflieven.humanresource.*;
import laboflieven.humanresource.heuristics.CountInstructionHeuristic;
import laboflieven.humanresource.instructions.*;
import laboflieven.humanresource.model.HumanInstructionSet;

import java.util.HashMap;
import java.util.List;

public class Level19 {

    public static void main(String[] args)
    {
        int maxNr = 10;
        var input = new HumanInOutput();
        input.input = List.of(5,-3,0);
        input.output = List.of(5,4,3,2,1,0,-3,-2,-1,0,0);

        var availableInstructions = List.of(
                HumanInstructionSet.INBOX,
                HumanInstructionSet.OUTBOX,
                HumanInstructionSet.JumpIfZero,
                HumanInstructionSet.JumpIfNegative,
                HumanInstructionSet.LOOP,
                HumanInstructionSet.CopyTo,
                HumanInstructionSet.CopyFrom,
                HumanInstructionSet.BumpMin,
                HumanInstructionSet.BumpPlus
        ).toArray(new HumanInstructionSet[0]);

        var maxMap = new HashMap<Class<?>, Integer>();
        maxMap.put(Inbox.class, 1);
        maxMap.put(Jump.class,2);
        maxMap.put(JumpIfZero.class,1);
        maxMap.put(JumpIfNegative.class,1);
        maxMap.put(CopyFrom.class,1);
        maxMap.put(CopyTo.class,1);
        maxMap.put(Outbox.class, 2);
        maxMap.put(BumpMin.class, 1);
        maxMap.put(BumpPlus.class, 1);
        var heuristic = new CountInstructionHeuristic(maxMap);
        var initRegisters = new HashMap<String, Integer>();
        initRegisters.put("r1", 1);
        initRegisters.put("r2", 0);
        var evaluator = new HumanProgramFitnessExaminer(List.of(input)
                ,300, new HumanStatementRunner(maxNr, initRegisters));
        //var iterator = new HumanPriorityProgramIterator();
        var iterator = new BruteForceProgramIterator(evaluator, availableInstructions, heuristic);

        Configuration config = new Configuration();
        config.setNumberOfRegisters(1);

        //iterator.iterate(config, availableInstructions, evaluator);
        iterator.iterate(1, 10);
    }
}
