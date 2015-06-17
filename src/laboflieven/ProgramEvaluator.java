package laboflieven;

import laboflieven.statements.Instruction;
import laboflieven.statements.Register;

import java.util.List;

/**
 * Created by Lieven on 14/06/2015.
 */
public class ProgramEvaluator
{
    private List<InOutParameters> conditions;

    /**
     * @param conditions Conditions that define the input parameters & the expected outcome.
     */
    public ProgramEvaluator(List<InOutParameters> conditions)
    {
        this.conditions = conditions;
    }

    public boolean evaluate(List<Instruction> instructions, List<Register> registers)
    {
        StatementRunner runner = new StatementRunner();
        Program program = new Program(instructions, registers);

        for(InOutParameters parameter : conditions)
        {
            runner.execute(program, parameter.input);
            for (Register register : program.getRegisters())
            {
                if (parameter.expectedOutput.containsKey(register.name) && //Has a value, but it's not what was expected.
                    parameter.expectedOutput.get(register.name) != register.value)
                {
                    return false;
                }
            }
        }
        return true;
    }
}
