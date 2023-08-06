package laboflieven.statements;

import laboflieven.InstructionMark;
import laboflieven.Program;
import laboflieven.runners.RegularStatementRunner;
import laboflieven.runners.StatementRunner;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class StatementRunnerTest {

    @Test
    public void testExecute() throws Exception {
        List<Register> registers = Register.createRegisters(4);
        Map<String, Double> results = getMap(2.0, 3.0, 0.0, 0.0);

        List<InstructionMark> instructions = new ArrayList<InstructionMark>();

        StatementRunner runner = new RegularStatementRunner();
        instructions.add(new Add(registers.get(0), registers.get(1)));
        Program program = new Program(instructions, registers);
        runner.execute(program, results);
        assertEquals(5.0, program.getRegisters().get(1).value, 0.0);
    }


    private static Map<String, Double> getMap(double a,double b,double c,double d)
    {
        Map<String, Double> results = new HashMap<String, Double>();
        results.put("R1", a);
        results.put("R2", b);
        results.put("R3", c);
        results.put("R4", d);
        return results;
    }
    @Test
    public void testExecuteQuadrant() throws Exception {
        List<Register> registers = Register.createRegisters(4);
        Map<String, Double> results = getMap(1.0,2.0,-3.0,0.0);

        StatementRunner runner = new RegularStatementRunner();

        List<InstructionMark> instructions = getInstructionsQuadrant(
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
        assertEquals(1, program.getRegisters().get(3).value, 0.0);
    }

    //@Test(expected=IllegalArgumentException.class)
    public void testExecuteRunnerThrows() {
        StatementRunner runner = new RegularStatementRunner();
        List<InstructionMark> instructions = new ArrayList<>();
        Register register1 = new Register("R1");
        instructions.add(new Sqrt(register1));
        List<Register> registers = new ArrayList<>();
        registers.add(register1);
        Program program = new Program(instructions, registers);
        Map<String, Double> results = new HashMap<>();
        runner.execute(program, results);
    }

    @Test
    public void testExecuteRunnerJumps() {
        StatementRunner runner = new RegularStatementRunner();
        List<InstructionMark> instructions = new ArrayList<>();
        Register register1 = new Register("R0");
        register1.value = 0;
        Register register2 = new Register("R1");
        register2.value = 2;
        instructions.add(new JumpIfRegister1Zero(register1, register2));
        List<Register> registers = new ArrayList<>();
        registers.add(register1);
        registers.add(register2);
        Program program = new Program(instructions, registers);
        Map<String, Double> results = new HashMap<>();
        results.put("R0", 0.0);
        results.put("R1", 2.0);
        runner.execute(program, results);
    }

    @Test
    public void testExecuteQuadrantOther() throws Exception {
        List<Register> registers = Register.createRegisters(4);

        Map<String, Double> results = getMap(2.0,-8.0,-24.0,0.0);

        StatementRunner runner = new RegularStatementRunner();

        List<InstructionMark> instructions = getInstructionsQuadrant(
                registers.get(0),
                registers.get(1),
                registers.get(2),
                registers.get(3)
        );
        Program program = new Program(instructions, registers);
        runner.execute(program, results);
        assertEquals(6, program.getRegisters().get(3).value, 0.0);
    }

    private List<InstructionMark> getInstructionsQuadrant(Register r1, Register r2, Register r3, Register r4) {
        List<InstructionMark> instructions = new ArrayList<InstructionMark>();

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