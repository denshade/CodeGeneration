package laboflieven.runners;

import laboflieven.InstructionMark;
import laboflieven.Program;
import laboflieven.accinstructions.AccRegisterInstruction;
import laboflieven.statements.Register;

import java.util.List;
import java.util.Map;

/**
 * Created by lveeckha on 31/05/2015.
 */
public class AccStatementRunner implements StatementRunner {

    public int MAXINSTRUCT;

    public AccStatementRunner()
    {
        this(100);
    }
    public AccStatementRunner(int maxExec)
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
        List<InstructionMark> instructions = program.getInstructions();
        Register left = new Register("AL");
        Register right = new Register("AR");
        int ip = 0;
        int instructionsRun = 0;
        int size = instructions.size();
        while ( ip < size)
        {
            instructionsRun++;
            if (instructionsRun > MAXINSTRUCT)
            {
                break;
            }
            AccRegisterInstruction instruction = (AccRegisterInstruction) instructions.get(ip);
            Integer pointer = instruction.execute(left, right, ip);
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