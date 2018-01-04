package laboflieven.humanresource;

import laboflieven.humanresource.RandomGeneticProgramIterator;
import laboflieven.humanresource.model.HumanInstructionEnum;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class RandomGeneticProgramIteratorTest
{

    @Test
    public void testBump()
    {

        List<Integer> inbox = new ArrayList<Integer>(4);
            inbox.add(2);
            inbox.add(-8);
            inbox.add(0);
            inbox.add(1);
        List<Integer> outbox = new ArrayList<Integer>();
            outbox.add(2);
            outbox.add(1);
            outbox.add(0);
            outbox.add(-8);
            outbox.add(-7);
            outbox.add(-6);
            outbox.add(-5);
            outbox.add(-4);
            outbox.add(-3);
            outbox.add(-2);
            outbox.add(-1);
            outbox.add(0);
            outbox.add(0);
            outbox.add(1);
            outbox.add(0);
        HumanInOutput inout  = new HumanInOutput();
        inout.input = inbox;
        inout.output = outbox;

        RandomGeneticProgramIterator it  = new RandomGeneticProgramIterator(new HumanProgramFitnessExaminer(Arrays.asList(inout),82),
                new HumanInstructionEnum[] {
                        HumanInstructionEnum.INBOX,
                        HumanInstructionEnum.OUTBOX,
                        HumanInstructionEnum.LOOP,
                        HumanInstructionEnum.BumpMin,
                        HumanInstructionEnum.BumpPlus,
                        HumanInstructionEnum.JumpIfZero,
                        HumanInstructionEnum.JumpIfNegative
                },
                1000,1000,1000
        );
        it.iterate(1, 10);
    }

}