package laboflieven.humanresource.challenges;

import laboflieven.humanresource.BruteForceProgramIterator;
import laboflieven.humanresource.HumanInOutput;
import laboflieven.humanresource.HumanProgramFitnessExaminer;
import laboflieven.humanresource.instructions.*;
import laboflieven.humanresource.model.HumanInstructionEnum;
import laboflieven.humanresource.model.HumanRegister;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//Found a program: [Inbox, CopyTo r0, Add, CopyTo r0, Add, CopyTo r0, Add, Outbox, Jump to 0]
public class Level12 {
    public static void main(String[] args)
    {
        int maxNr = 20;
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


        var heuristic = new CountInstructionHeuristic(map);
        var iterator = new BruteForceProgramIterator(evaluator,
                List.of(HumanInstructionEnum.INBOX, HumanInstructionEnum.OUTBOX, HumanInstructionEnum.CopyTo, HumanInstructionEnum.LOOP, HumanInstructionEnum.ADD).toArray(new HumanInstructionEnum[0]),
                heuristic);
        iterator.iterate(2, maxNr);
        List<HumanRegister> registers = new ArrayList();
        HumanRegister r0 = new HumanRegister("r0");
        registers.add(r0);
    }
}
