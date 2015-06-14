package laboflieven;

import laboflieven.statements.Instruction;
import laboflieven.statements.Register;
import java.util.Map;

/**
 * Created by lveeckha on 31/05/2015.
 */
public class StatementRunner {

    /**
     *
     * @param registerValues name => Value pairs.
     */
    public void execute(Program program, Map<String, Double> registerValues)
    {
        for (Register register : program.getRegisters())
        {
            if (!registerValues.containsKey(register.name)){
                throw new IllegalArgumentException("You have not specified the initial values for " + register.name);
            }
            register.value = registerValues.get(register.name);
        }
        program.getInstructions().forEach(laboflieven.statements.Instruction::execute);
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