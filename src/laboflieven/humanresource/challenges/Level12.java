package laboflieven.humanresource.challenges;

import laboflieven.humanresource.BruteForceProgramIterator;
import laboflieven.humanresource.HumanInOutput;
import laboflieven.humanresource.HumanProgramFitnessExaminer;
import laboflieven.humanresource.HumanStatementRunner;
import laboflieven.humanresource.heuristics.StartInboxOutboxLoopCountInstructionHeuristic;
import laboflieven.humanresource.instructions.*;
import laboflieven.humanresource.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

//Found a program: [Inbox, CopyTo r0, Add, CopyTo r0, Add, CopyTo r0, Add, Outbox, Jump to 0]
public class Level12 {

    public static void main(String[] args) throws InvalidProgramException {
        int maxNr = 14;
        var input = new HumanInOutput();
        input.input = List.of(5,-2,7,0);
        input.output = List.of(5*40,-2*40,7*40,0*40);
        var evaluator = new HumanProgramFitnessExaminer(List.of(input)
                ,200);
        var map = new HashMap<Class, Integer>();
        map.put(Inbox.class, 1);
        map.put(CopyTo.class, maxNr-3);
        map.put(Add.class, maxNr-3);
        map.put(Outbox.class, 1);
        map.put(Jump.class, 1);


        var heuristic = new StartInboxOutboxLoopCountInstructionHeuristic(map, maxNr);

        var iterator = new BruteForceProgramIterator(evaluator,
                List.of(HumanInstructionEnum.INBOX, HumanInstructionEnum.OUTBOX, HumanInstructionEnum.CopyTo, HumanInstructionEnum.JUMP, HumanInstructionEnum.ADD).toArray(new HumanInstructionEnum[0]),
                heuristic);
        iterator.iterate(2, maxNr);

        List<HumanRegister> registers = new ArrayList();
        HumanRegister r0 = new HumanRegister("r0");
        registers.add(r0);
        /*var iterator = new HumanPriorityProgramIterator();
        Configuration config = new Configuration();
        iterator.iterate(config, List.of(HumanInstructionEnum.INBOX, HumanInstructionEnum.OUTBOX, HumanInstructionEnum.CopyTo, HumanInstructionEnum.LOOP, HumanInstructionEnum.ADD).toArray(new HumanInstructionEnum[0]),
                evaluator);*/
        HumanRegister r1 = new HumanRegister("r0");
        registers.add(r0);
        registers.add(r1);
        var l = new ArrayList<HumanInstruction>();
        l.add(new Inbox()); // 1
        l.add(new CopyTo(r0)); // 2
        l.add(new Add(r0)); //3
        l.add(new CopyTo(r0)); //4
        l.add(new Add(r0)); //5
        l.add(new CopyTo(r0)); //6
        l.add(new Add(r0)); //7
        l.add(new CopyTo(r1)); //8
        l.add(new CopyTo(r0)); //9
        l.add(new Add(r0)); //10
        l.add(new CopyTo(r0)); //11
        l.add(new Add(r0)); //12
        l.add(new CopyTo(r0)); //13
        l.add(new Add(r1)); //14
        l.add(new Outbox()); //15
        l.add(new Jump(0)); //16
        HumanStatementRunner runner = new HumanStatementRunner();
        Queue<Integer> outQ = new ArrayBlockingQueue<>(4);
        Queue<Integer> inQ = new ArrayBlockingQueue<>(4);
        inQ.addAll(input.input);
        System.out.println(evaluator.isFit(l, registers));

        runner.execute(new HumanResourceProgram(l, registers), inQ, outQ);
        System.out.println(outQ);
    }
}
