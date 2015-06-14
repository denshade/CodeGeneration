import statements.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class StatementRunnerTest {

    @org.junit.Test
    public void testExecute() throws Exception {
        List<Register> registers = Register.create4Registers();
        Map<String, Double> results = new HashMap<String, Double>();
        results.put("1", 2.0);
        results.put("2", 3.0);


        List<Instruction> instructions = new ArrayList<Instruction>();

        StatementRunner runner = new StatementRunner();
        instructions.add(new Add(registers.get(0), registers.get(1)));
        Program program = new Program(instructions, registers);
        runner.execute(program, results);
        assertEquals(5.0, registers.get(1).value, 0.0);
    }

    @org.junit.Test
    public void testExecuteQuadrant() throws Exception {
        List<Register> registers = Register.create4Registers();
        Map<String, Double> results = new HashMap<String, Double>();
        results.put("1", 1.0);
        results.put("2", 2.0);
        results.put("3", -3.0);
        results.put("4", 0.0);

        StatementRunner runner = new StatementRunner();

        List<Instruction> instructions = getInstructionsQuadrant(
                registers.get(0),
                registers.get(1),
                registers.get(2),
                registers.get(3)
        );
        Program program = new Program(instructions, registers);

        runner.execute(
                program,
                results
        );
        assertEquals(1, registers.get(3).value, 0.0);
    }

    @org.junit.Test
    public void testExecuteQuadrantOther() throws Exception {
        List<Register> registers = Register.create4Registers();

        Map<String, Double> results = new HashMap<String, Double>();
        results.put("1", 2.0);
        results.put("2", -8.0);
        results.put("3", -24.0);
        results.put("4", 0.0);


        StatementRunner runner = new StatementRunner();

        List<Instruction> instructions = getInstructionsQuadrant(
                registers.get(0),
                registers.get(1),
                registers.get(2),
                registers.get(3)
        );
        Program program = new Program(instructions, registers);
        runner.execute(program, results);
        assertEquals(6, program.getRegisters().get(3).value, 0.0);
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