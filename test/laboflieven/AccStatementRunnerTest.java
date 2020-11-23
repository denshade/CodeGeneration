package laboflieven;

import laboflieven.accinstructions.*;
import laboflieven.challenges.TestCases;
import laboflieven.runners.AccStatementRunner;
import laboflieven.statements.Register;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccStatementRunnerTest {

    @org.junit.Test
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
    private static void l(double l, double r, double r0)
    {
        System.out.println(l+","+r+","+r0);
    }

    public double nand(double leftvalue, double rightvalue)
    {
        boolean sourceB = leftvalue < 0.0000001 ? false: true;
        boolean destinationB = rightvalue < 0.0000001 ? false: true;
        boolean evaluation = !(sourceB && destinationB);
        return  evaluation?1.0:0.0;
    }

}