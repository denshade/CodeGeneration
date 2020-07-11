package laboflieven.humanresource;

import laboflieven.humanresource.instructions.*;
import laboflieven.humanresource.model.HumanInstruction;
import laboflieven.humanresource.model.HumanRegister;
import laboflieven.humanresource.model.HumanResourceProgram;
import laboflieven.humanresource.model.InvalidProgramException;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import static org.junit.Assert.*;

/**
 * Created by Lieven on 2-1-2018.
 */
public class HumanStatementRunnerTest
{
    @Test
    public void testFirstProgram() throws InvalidProgramException {
        ArrayList<HumanInstruction> instructions = new ArrayList<>();
        instructions.add(new Inbox());
        instructions.add(new Outbox());
        instructions.add(new Inbox());
        instructions.add(new Outbox());
        instructions.add(new Inbox());
        instructions.add(new Outbox());
        HumanResourceProgram program = new HumanResourceProgram(instructions, new ArrayList<>());

        HumanStatementRunner runner = new HumanStatementRunner();
        Queue<Integer> outbox = new ArrayBlockingQueue<Integer>(1000);

        Queue<Integer> inbox = new ArrayBlockingQueue<Integer>(1000);
        inbox.add(4);
        inbox.add(1);
        inbox.add(2);
        runner.execute(program, 0, inbox, outbox);
        assertEquals(Integer.valueOf(4), outbox.poll());
        assertEquals(Integer.valueOf(1), outbox.poll());
        assertEquals(Integer.valueOf(2), outbox.poll());
    }

    @Test
    public void testMaxFirstProgram() throws InvalidProgramException {
        ArrayList<HumanInstruction> instructions = new ArrayList<>();
        instructions.add(new Inbox());
        instructions.add(new Outbox());
        instructions.add(new Inbox());
        instructions.add(new Outbox());
        instructions.add(new Inbox());
        instructions.add(new Outbox());
        HumanResourceProgram program = new HumanResourceProgram(instructions, new ArrayList<>());

        HumanStatementRunner runner = new HumanStatementRunner(5);
        Queue<Integer> outbox = new ArrayBlockingQueue<Integer>(1000);

        Queue<Integer> inbox = new ArrayBlockingQueue<Integer>(1000);
        inbox.add(4);
        inbox.add(1);
        inbox.add(2);
        runner.execute(program, 0, inbox, outbox);
        assertEquals(Integer.valueOf(4), outbox.poll());
        assertEquals(Integer.valueOf(1), outbox.poll());
        assertTrue(outbox.size() == 0);
    }

    @Test
    public void testSecondLoopProgram() throws InvalidProgramException {
        ArrayList<HumanInstruction> instructions = new ArrayList<>();
        instructions.add(new Inbox());
        instructions.add(new Outbox());
        instructions.add(new Jump(0));
        HumanResourceProgram program = new HumanResourceProgram(instructions, new ArrayList<>());


        HumanStatementRunner runner = new HumanStatementRunner();
        Queue<Integer> outbox = new ArrayBlockingQueue<Integer>(3);

        Queue<Integer> inbox = new ArrayBlockingQueue<Integer>(3);
        inbox.add(4);
        inbox.add(1);
        inbox.add(2);
        runner.execute(program, 0, inbox, outbox);
        assertEquals(Integer.valueOf(4), outbox.poll());
        assertEquals(Integer.valueOf(1), outbox.poll());
        assertEquals(Integer.valueOf(2), outbox.poll());
    }


    @Test
    public void testCopyFromProgram() throws InvalidProgramException {
        List<HumanRegister> registers = new ArrayList<>();
        registers.add(new HumanRegister("0"));
        registers.add(new HumanRegister("1"));
        registers.add(new HumanRegister("2"));
        registers.add(new HumanRegister("3"));

        registers.get(0).value = 0;
        registers.get(1).value = 1;
        registers.get(2).value = 2;
        registers.get(3).value = 3;

        ArrayList<HumanInstruction> instructions = new ArrayList<>();
        instructions.add(new CopyFrom(registers.get(1)));
        instructions.add(new Outbox());
        instructions.add(new CopyFrom(registers.get(2)));
        instructions.add(new Outbox());
        HumanResourceProgram program = new HumanResourceProgram(instructions, new ArrayList<>());


        HumanStatementRunner runner = new HumanStatementRunner();
        Queue<Integer> outbox = new ArrayBlockingQueue<Integer>(2);

        Queue<Integer> inbox = new ArrayBlockingQueue<Integer>(2);

        runner.execute(program, 0, inbox, outbox);
        assertEquals(Integer.valueOf(1), outbox.poll());
        assertEquals(Integer.valueOf(2), outbox.poll());
    }


    @Test
    public void testCopyToProgram() throws InvalidProgramException {
        List<HumanRegister> registers = new ArrayList<>();
        registers.add(new HumanRegister("0"));
        registers.add(new HumanRegister("1"));
        registers.add(new HumanRegister("2"));

        registers.get(0).value = null;
        registers.get(1).value = null;
        registers.get(2).value = null;

        ArrayList<HumanInstruction> instructions = new ArrayList<>();

        instructions.add(new Inbox());
        instructions.add(new CopyTo(registers.get(0)));
        instructions.add(new Inbox());
        instructions.add(new Outbox());
        instructions.add(new CopyFrom(registers.get(0)));
        instructions.add(new Outbox());
        instructions.add(new Jump(0));

        HumanResourceProgram program = new HumanResourceProgram(instructions, new ArrayList<>());


        HumanStatementRunner runner = new HumanStatementRunner();
        Queue<Integer> outbox = new ArrayBlockingQueue<Integer>(1000);

        Queue<Integer> inbox = new ArrayBlockingQueue<Integer>(1000);
        inbox.add(3);
        inbox.add(9);
        inbox.add(2);
        inbox.add(1);

        runner.execute(program, 0, inbox, outbox);
        assertEquals(Integer.valueOf(9), outbox.poll());
        assertEquals(Integer.valueOf(3), outbox.poll());
        assertEquals(Integer.valueOf(1), outbox.poll());
        assertEquals(Integer.valueOf(2), outbox.poll());
    }

    @Test
    public void testAddProgram() throws InvalidProgramException {
        List<HumanRegister> registers = new ArrayList<>();
        registers.add(new HumanRegister("0"));
        registers.add(new HumanRegister("1"));
        registers.add(new HumanRegister("2"));

        registers.get(0).value = null;
        registers.get(1).value = null;
        registers.get(2).value = null;

        ArrayList<HumanInstruction> instructions = new ArrayList<>();

        instructions.add(new Inbox());
        instructions.add(new CopyTo(registers.get(0)));
        instructions.add(new Inbox());
        instructions.add(new Add(registers.get(0)));
        instructions.add(new Outbox());
        instructions.add(new Jump(0));

        HumanResourceProgram program = new HumanResourceProgram(instructions, new ArrayList<>());


        HumanStatementRunner runner = new HumanStatementRunner();
        Queue<Integer> outbox = new ArrayBlockingQueue<Integer>(1000);

        Queue<Integer> inbox = new ArrayBlockingQueue<Integer>(1000);
        inbox.add(3);
        inbox.add(9);
        inbox.add(2);
        inbox.add(1);

        runner.execute(program, 0, inbox, outbox);
        assertEquals(Integer.valueOf(12), outbox.poll());
        assertEquals(Integer.valueOf(3), outbox.poll());
    }


    @Test
    public void testSubProgram() throws InvalidProgramException {
        List<HumanRegister> registers = new ArrayList<>();
        registers.add(new HumanRegister("0"));
        registers.add(new HumanRegister("1"));
        registers.add(new HumanRegister("2"));

        registers.get(0).value = null;
        registers.get(1).value = null;
        registers.get(2).value = null;

        ArrayList<HumanInstruction> instructions = new ArrayList<>();

        instructions.add(new Inbox());
        instructions.add(new CopyTo(registers.get(0)));
        instructions.add(new Inbox());
        instructions.add(new CopyTo(registers.get(1)));
        instructions.add(new Sub(registers.get(0)));
        instructions.add(new Outbox());
        instructions.add(new CopyFrom(registers.get(0)));
        instructions.add(new Sub(registers.get(1)));
        instructions.add(new Outbox());
        instructions.add(new Jump(0));

        HumanResourceProgram program = new HumanResourceProgram(instructions, new ArrayList<>());


        HumanStatementRunner runner = new HumanStatementRunner();
        Queue<Integer> outbox = new ArrayBlockingQueue<Integer>(1000);

        Queue<Integer> inbox = new ArrayBlockingQueue<Integer>(1000);
        inbox.add(3);
        inbox.add(9);
        inbox.add(6);
        inbox.add(3);

        runner.execute(program, 0, inbox, outbox);
        assertEquals(Integer.valueOf(6), outbox.poll());
        assertEquals(Integer.valueOf(-6), outbox.poll());
        assertEquals(Integer.valueOf(-3), outbox.poll());
        assertEquals(Integer.valueOf(3), outbox.poll());
    }
    @Test
    public void testJumpIfZeroProgram() throws InvalidProgramException {

        ArrayList<HumanInstruction> instructions = new ArrayList<>();

        instructions.add(new Inbox());
        instructions.add(new JumpIfZero(0));
        instructions.add(new Outbox());
        instructions.add(new Jump(0));

        HumanResourceProgram program = new HumanResourceProgram(instructions, new ArrayList<>());


        HumanStatementRunner runner = new HumanStatementRunner();
        Queue<Integer> outbox = new ArrayBlockingQueue<Integer>(1000);

        Queue<Integer> inbox = new ArrayBlockingQueue<Integer>(1000);
        inbox.add(3);
        inbox.add(0);
        inbox.add(2);

        runner.execute(program, 0, inbox, outbox);
        assertEquals(Integer.valueOf(3), outbox.poll());
        assertEquals(Integer.valueOf(2), outbox.poll());
    }
}