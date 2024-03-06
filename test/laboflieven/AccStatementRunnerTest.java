package laboflieven;

import laboflieven.challenges.TestCases;
import laboflieven.instructions.accinstructions.*;
import laboflieven.registers.NumberNamingScheme;
import laboflieven.runners.AccStatementRunner;
import laboflieven.registers.Register;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AccStatementRunnerTest {

    @Test
    public void testMyStatement()
    {
        AccStatementRunner r = new AccStatementRunner();
        TestCases.getExampleInput1D();
        List<Register> registers = new ArrayList<>();
        Register r0 = new Register("r0");
        registers.add(r0);
        List<InstructionMark> instructions = new ArrayList<>();
        instructions.add(new Cos());
        instructions.add(new Add());
        instructions.add(new LoadAccLeftIntoRegister(r0));
        instructions.add(new Add());
        instructions.add(new LoadIntoRightAcc(r0));
        instructions.add(new LoadAccLeftIntoRegister(r0));
        instructions.add(new Nand());
        instructions.add(new Add());
        instructions.add(new JumpIfLteStart());
//        instructions.add(new AccLeftPull(r0));
        Program p = new Program(instructions, registers);
        Map<String, Double> doubleMap = new HashMap<>();
        doubleMap.put("r0", 0.0);
        r.execute(p, doubleMap);
        System.out.println(r0.value);
        System.out.println(p.getInstructions());
    }

    @Test
    public void testResult()
    {
        double r0 = 0;
        double left = 0;
        double right = 0;
        int l = 0;
        do
        {
            left = Math.cos(left);
            left += right;
            r0 = left;
            left += right;
            right = r0;
            r0 = left;
            left = nand(left,right);
            left += right;
            if (l++ == 1000) {
                System.out.println(r0);
                return;
            }
        } while(left <= right); //JumpIfLteStart
    }
    @Test
    void NanUponOverflow()
    {
        var runner = new AccStatementRunner();
        var program = new Program(List.of(new JumpIfGteStart()), new NumberNamingScheme().createRegisters(1));
        var result = runner.execute(program, Map.of("R1", 1.0));
        Assertions.assertTrue(Double.isNaN(result.get("R1")));
    }

    @Test
    void checkJumpInstructionWorks()
    {
        var runner = new AccStatementRunner();
        var program = new Program(List.of(new Jump2IfEq(), new Add()), new NumberNamingScheme().createRegisters(1));
        var result = runner.execute(program, Map.of("R1", 1.0, "R2", 2.0));
        Assertions.assertEquals(1.0, result.get("R1"), 0.001);
    }
    private static void l(double l, double r, double r0)
    {
        System.out.println(l+","+r+","+r0);
    }

    public double nand(double leftvalue, double rightvalue)
    {
        boolean sourceB = !(leftvalue < 0.0000001);
        boolean destinationB = !(rightvalue < 0.0000001);
        boolean evaluation = !(sourceB && destinationB);
        return  evaluation?1.0:0.0;
    }

}