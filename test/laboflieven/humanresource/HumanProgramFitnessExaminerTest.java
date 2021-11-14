package laboflieven.humanresource;

import laboflieven.humanresource.instructions.*;
import laboflieven.humanresource.model.HumanInstruction;
import laboflieven.humanresource.model.HumanRegister;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class HumanProgramFitnessExaminerTest
{

    @Test
    public void checkGoodProgram()
    {
        List<Integer> inbox = new ArrayList<Integer>(3);
        inbox.add(6);
        inbox.add(9);
        inbox.add(1);
        inbox.add(1);
        inbox.add(3);
        inbox.add(0);
        inbox.add(2);
        inbox.add(2);
        List<Integer> outbox = new ArrayList<Integer>();
        outbox.add(1);
        outbox.add(2);

        List<HumanRegister> registers = new ArrayList<>();
        registers.add(new HumanRegister("R0"));

        HumanInOutput inout2  = new HumanInOutput();
        inout2.input = Collections.unmodifiableList(inbox);
        inout2.output = Collections.unmodifiableList(outbox);
        List<HumanInstruction> instructions = new ArrayList<>();
        instructions.add(new Inbox());
        instructions.add(new CopyTo(registers.get(0)));
        instructions.add(new Inbox());
        instructions.add(new Add(registers.get(0)));
        instructions.add(new JumpIfZero(2));
        instructions.add(new Inbox());
        instructions.add(new JumpIfZero(4));
        instructions.add(new Outbox());
        instructions.add(new Jump(0));
        HumanProgramFitnessExaminer ex = new HumanProgramFitnessExaminer(Arrays.asList(inout2), 27);
        assertTrue(ex.isFit(instructions, registers));
    }
}