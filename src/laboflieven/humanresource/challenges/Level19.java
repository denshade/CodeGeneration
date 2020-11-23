package laboflieven.humanresource.challenges;

import laboflieven.common.Configuration;
import laboflieven.humanresource.*;
import laboflieven.humanresource.heuristics.CountInstructionHeuristic;
import laboflieven.humanresource.heuristics.CountInstructionWithJumpsHeuristic;
import laboflieven.humanresource.instructions.*;
import laboflieven.humanresource.model.HumanInstruction;
import laboflieven.humanresource.model.HumanInstructionSet;
import laboflieven.humanresource.model.HumanRegister;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Level19 {

    public static void main(String[] args)
    {
        Level l = Level.WARNING;
        Logger rootLogger = LogManager.getLogManager().getLogger("");
        rootLogger.setLevel(l);
        for (Handler h : rootLogger.getHandlers()) {
            h.setLevel(l);
        }
        int maxNr = 12;
        var input = new HumanInOutput();
        input.input = List.of(5,-3,0);
        input.output = List.of(5,4,3,2,1,0,-3,-2,-1,0,0);

        var availableInstructions = List.of(
                HumanInstructionSet.INBOX,
                HumanInstructionSet.OUTBOX,
                HumanInstructionSet.JumpIfZero,
                HumanInstructionSet.JumpIfNegative,
                HumanInstructionSet.JUMP,
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
        maxMap.put(Outbox.class, 3);
        maxMap.put(BumpMin.class, 1);
        maxMap.put(BumpPlus.class, 1);


        var heuristic = new CountInstructionWithJumpsHeuristic(maxMap, Arrays.asList(
                "Outbox, Outbox",
                "BumpMin r0, BumpPlus r0",
                "BumpPlus r0, BumpMin r0",
                "CopyTo r0, CopyFrom r0",
                "CopyFrom r0, CopyTo r0"
        ));

        var evaluator = new HumanProgramFitnessExaminer(List.of(input)
                ,300, new HumanStatementRunner());
        //fakeIterate(heuristic, evaluator);

        //var iterator = new HumanPriorityProgramIterator();
        var iterator = new BruteForceProgramIterator(evaluator, availableInstructions, heuristic);

        Configuration config = new Configuration();
        config.setNumberOfRegisters(1);

        //iterator.iterate(config, availableInstructions, evaluator);
        iterator.iterate(1, 10);
    }

    private static void fakeIterate(CountInstructionWithJumpsHeuristic heuristic, HumanProgramFitnessExaminer evaluator) {
    /*
    -- HUMAN RESOURCE MACHINE PROGRAM --
a:
INBOX
COPYTO   0
OUTBOX
b:
c:
COPYFROM 0
JUMPZ    a
JUMPN    d
BUMPDN   0
OUTBOX
JUMP     c
d:
BUMPUP   0
OUTBOX
JUMP     b
     */
        HumanRegister r0 = new HumanRegister("r0");
        var solution = new ArrayList<HumanInstruction>();
        solution.add(new Inbox()); // 0
        solution.add(new CopyTo(r0)); //1
        solution.add(new Outbox()); // 2
        solution.add(new CopyFrom(r0)); // 3
        solution.add(new JumpIfZero(0)); // 4
        solution.add(new JumpIfNegative(9)); //5
        solution.add(new BumpMin(r0)); //6
        solution.add(new Outbox()); //7
        solution.add(new Jump(3)); //8
        solution.add(new BumpPlus(r0)); //9
        solution.add(new Outbox()); // 10
        solution.add(new Jump(3)); //11

        var it = new FakeProgramIterator(evaluator, solution, heuristic);
        it.iterate(1, 10);
    }
}
