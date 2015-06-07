import statements.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class StatementRunnerTest {

    @org.junit.Test
    public void testExecute() throws Exception {
        Register register1 = new Register("0");
        Register register2 = new Register("1");
        Register register3 = new Register("2");
        Register register4 = new Register("3");
        register1.value = 2;
        register2.value = 3;

        List<Register> registers = new ArrayList<Register>();
        registers.add(register1);
        registers.add(register2);
        registers.add(register3);
        registers.add(register4);

        List<Instruction> instructions = new ArrayList<Instruction>();

        StatementRunner runner = new StatementRunner();
        instructions.add(new Add(register1, register2));
        runner.execute(instructions, registers);
        assertEquals(5.0, registers.get(1).value, 0.0);
    }

    @org.junit.Test
    public void testExecuteQuadrant() throws Exception {
        Register r1 = new Register("1");//a
        Register r2 = new Register("2");//b
        Register r3 = new Register("3");//c
        Register r4 = new Register("4");//exit
        r1.value = 1;
        r2.value = 2;
        r3.value = -3;

        List<Register> registers = new ArrayList<Register>();
        registers.add(r1);
        registers.add(r2);
        registers.add(r3);
        registers.add(r4);
        StatementRunner runner = new StatementRunner();

        List<Instruction> instructions = getInstructionsQuadrant(r1, r2, r3, r4);

        runner.execute(instructions, registers);
        assertEquals(1, registers.get(3).value, 0.0);
    }

    @org.junit.Test
    public void testExecuteQuadrantOther() throws Exception {
        Register r1 = new Register("1");//a
        Register r2 = new Register("2");//b
        Register r3 = new Register("3");//c
        Register r4 = new Register("4");//exit
        r1.value = 2;
        r2.value = -8;
        r3.value = -24;

        List<Register> registers = new ArrayList<Register>();
        registers.add(r1);
        registers.add(r2);
        registers.add(r3);
        registers.add(r4);
        StatementRunner runner = new StatementRunner();

        List<Instruction> instructions = getInstructionsQuadrant(r1, r2, r3, r4);

        runner.execute(instructions, registers);
        assertEquals(6, registers.get(3).value, 0.0);
    }

    private List<Instruction> getInstructionsQuadrant(Register r1, Register r2, Register r3, Register r4) {
        List<Instruction> instructions = new ArrayList<Instruction>();

        // -b + sqrt(b^2 + 4*a*c) / 2*a
        instructions.add(new Mul(r1, r3)); //r1 = a, r2 = b, a*c => r3
        instructions.add(new Add(r3, r3)); //r1 = a, r2 = b, 2*a*c  => r3
        instructions.add(new Add(r3, r3)); //r1 = a, r2 = b, 4*a*c  => r3
        instructions.add(new Move(r3, r4)); //r1 = a, r2 = b, 4*a*c  => r3, 4*a*c  => r4,
        instructions.add(new Move(r2, r3)); //r1 = a, r2 = b, b => r3, 4*a*c  => r4,

        instructions.add(new Mul(r3,r3)); //r1 = a, r2 = b, b^2 => r3, 4*a*c  => r4,
        instructions.add(new Invert(r4));//r1 = a, r2 = b, b^2 => r3, -4*a*c  => r4,
        instructions.add(new Add(r3,r4));//r1 = a, r2 = b, b^2 => r3, b^2 - 4*a*c  => r4,
        instructions.add(new Sqrt(r4));//r1 = a, r2 = b, b^2 => r3, sqrt(b^2 - 4*a*c)  => r4, //OK
        instructions.add(new Add(r1, r1)); //r1 = 2a, r2 = b, b^2 => r3, sqrt(b^2 - 4*a*c)  => r4,//OK

        instructions.add(new Invert(r2)); //r1 = 2a, r2 = -b, b => r3, sqrt(b^2 - 4*a*c)  => r4,
        instructions.add(new Add(r2, r4));//r1 = 2a, r2 = b, -b => r3, -b + sqrt(b^2 - 4*a*c)  => r4,
        instructions.add(new Div(r1, r4));//r1 = 2a, r2 = b, -b => r3, -b + sqrt(b^2 - 4*a*c)  => r4,
        return instructions;
    }
}