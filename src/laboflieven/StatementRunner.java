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