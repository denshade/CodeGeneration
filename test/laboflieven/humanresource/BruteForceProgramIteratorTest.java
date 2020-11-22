package laboflieven.humanresource;


import laboflieven.humanresource.model.HumanInstructionSet;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


/**
 * Created by Lieven on 3-1-2018.
 */
public class BruteForceProgramIteratorTest
{
    @Test
    public void testBruteLoopProgram()
    {
        List<Integer> inbox = new ArrayList<Integer>(3);
        inbox.add(4);
        inbox.add(1);
        inbox.add(2);
        List<Integer> outbox = new ArrayList<Integer>(inbox);

        HumanInOutput inout  = new HumanInOutput();
        inout.input = inbox;
        inout.output = outbox;

        BruteForceProgramIterator it  = new BruteForceProgramIterator(new HumanProgramFitnessExaminer(Arrays.asList(inout),3), new HumanInstructionSet[] {HumanInstructionSet.INBOX, HumanInstructionSet.OUTBOX, HumanInstructionSet.LOOP});
        it.iterate(0, 3);
    }

    @Test
    public void testCountDownBruteLoopProgram()
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

        BruteForceProgramIterator it  = new BruteForceProgramIterator(new HumanProgramFitnessExaminer(Arrays.asList(inout),82),
                new HumanInstructionSet[] {
                        HumanInstructionSet.INBOX,
                        HumanInstructionSet.OUTBOX,
                        HumanInstructionSet.LOOP,
                        HumanInstructionSet.BumpMin,
                        HumanInstructionSet.BumpPlus,
                        HumanInstructionSet.JumpIfZero,
                        HumanInstructionSet.JumpIfNegative
                }
        );
        it.iterate(1, 10);
    }


    //@Test
    public void testEqualizerLoopProgram()
    {
        List<Integer> inbox = new ArrayList<Integer>(8);
        inbox.add(8);
        inbox.add(4);
        inbox.add(2);
        inbox.add(2);
        inbox.add(-2);
        inbox.add(9);
        inbox.add(6);
        inbox.add(6);
        List<Integer> outbox = new ArrayList<Integer>();
        outbox.add(2);
        outbox.add(6);

        HumanInOutput inout1  = new HumanInOutput();
        inout1.input = Collections.unmodifiableList(inbox);
        inout1.output = Collections.unmodifiableList(outbox);

        List<Integer> inbox2 = new ArrayList<Integer>(8);
        inbox2.add(3);
        inbox2.add(3);
        inbox2.add(7);
        inbox2.add(7);
        inbox2.add(0);
        inbox2.add(-7);
        inbox2.add(1);
        inbox2.add(1);
        List<Integer> outbox2 = new ArrayList<Integer>();
        outbox2.add(3);
        outbox2.add(7);
        outbox2.add(1);

        HumanInOutput inout2  = new HumanInOutput();
        inout2.input = Collections.unmodifiableList(inbox2);
        inout2.output = Collections.unmodifiableList(outbox2);



        List<HumanInOutput> solutions = new ArrayList<>();
        solutions.add(inout1);
        solutions.add(inout2);

        BruteForceProgramIterator it  = new BruteForceProgramIterator(new HumanProgramFitnessExaminer(solutions,26), new HumanInstructionSet[]{
                HumanInstructionSet.INBOX,
                HumanInstructionSet.LOOP,
                HumanInstructionSet.CopyFrom,
                HumanInstructionSet.CopyTo,
                HumanInstructionSet.JumpIfZero,
                HumanInstructionSet.OUTBOX,
                HumanInstructionSet.Sub,
        });
        it.iterate(1, 10);
    }



}