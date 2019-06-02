package laboflieven;

import laboflieven.accinstructions.*;
import laboflieven.challenges.TestCases;
import laboflieven.statements.Register;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class AccStatementRunnerTest {

    @Test
    public void testMyStatement()
    {
        AccStatementRunner r = new AccStatementRunner();
        TestCases.getExampleInput1D();
        List<Register> registers = new ArrayList<>();
        Register r0 = new Register("r0");
        registers.add(r0);
        List<AccRegisterInstruction> instructions = new ArrayList<>();
        instructions.add(new Cos());
        instructions.add(new Add());
        instructions.add(new AccLeftPull(r0));
        instructions.add(new Add());
        instructions.add(new AccRightPush(r0));
        instructions.add(new AccLeftPull(r0));
        instructions.add(new Nand());
        instructions.add(new Add());
        instructions.add(new JumpIfLteStart());
        instructions.add(new AccLeftPull(r0));
        AccProgram p = new AccProgram(instructions, registers);
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
        do //left = left - right; ,  right = r0, r0 = left;, left = nand(left, right), left = left - right; ,  if left <=  R then goto 0 , r0 = left;
        {
            left = Math.cos(left); //cos
            left = left + right;  //sub
            r0 = left; //AccLeftPull
            left = left + right; //sub
            right = r0; // AccRightPush
            r0 = left; // AccLeftPull
            left = nand(left,right); //nand
            left = left + right; //sub
        } while(left <= right); //JumpIfLteStart
        r0 = left;
        System.out.println(r0);
    }

    public double nand(double leftvalue, double rightvalue)
    {
        boolean sourceB = leftvalue < 0.0000001 ? false: true;
        boolean destinationB = rightvalue < 0.0000001 ? false: true;
        boolean evaluation = !(sourceB && destinationB);
        return  evaluation?1.0:0.0;
    }

}