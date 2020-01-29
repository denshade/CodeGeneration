package laboflieven;

import laboflieven.statements.Instruction;
import laboflieven.statements.Register;

import java.util.List;
import java.util.Map;

/**
 * Created by lveeckha on 31/05/2015.
 */
public class StatementRunner {

    public StatementRunner()
    {

    }

    public int MAXINSTRUCT = 100;

    public StatementRunner(int maxExec)
    {
         MAXINSTRUCT =  maxExec;
    }


    /**
     *
     * @param registerValues name => Value pairs.
     */
    public void execute(Program program, Map<String, Double> registerValues)
    {
        program.initializeRegisters(registerValues);
        List<Instruction> instructions = program.getInstructions();
        int ip = 0;
        int instructionsRun = 0;
        int size = instructions.size();
        while ( ip < size)
        {
            instructionsRun++;
            if (instructionsRun > MAXINSTRUCT)
                break;
            Instruction instruction = instructions.get(ip);
            Integer pointer = instruction.execute();
            if (pointer != null && pointer >= 0)
            {
                ip = pointer;
            }
            else
            {
                ip++;
            }
        }
    }
}
/*      r1.value = 2;
        r2.value = -8;
        r3.value = -24;
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

 */