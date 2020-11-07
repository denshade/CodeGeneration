package laboflieven.humanresource;

import laboflieven.humanresource.instructions.Inbox;
import laboflieven.humanresource.model.Guy;
import laboflieven.humanresource.model.HumanInstruction;
import laboflieven.humanresource.model.HumanResourceProgram;
import laboflieven.humanresource.model.InvalidProgramException;

import java.util.List;
import java.util.Queue;

/**
 * Created by lveeckha on 31/05/2015.
 */
public class HumanStatementRunner {

    public HumanStatementRunner()
    {}

    public HumanStatementRunner(int maxInstructions)
    {
        MAXINSTRUCT = maxInstructions;
    }

    public int MAXINSTRUCT = 100;
    /**
     *
     */
    public void execute(HumanResourceProgram program, Queue<Integer> incomingQ, Queue<Integer> outgoingQ) throws InvalidProgramException
    {
        Guy guy = new Guy();
        int instructionsRun = 0;
        int ip = 0;
        final List<HumanInstruction> instructions = program.getInstructions();
        int size = instructions.size();
        while ( ip < size)
        {
            instructionsRun++;
            if (instructionsRun > MAXINSTRUCT)
            {
                //System.out.println("MAX INSTRUCT " + MAXINSTRUCT + " reached.");
                break;
            }
            HumanInstruction instruction = instructions.get(ip);
            if (instruction instanceof Inbox && incomingQ.size() == 0)
                break;
            Integer pointer = instruction.execute(incomingQ, outgoingQ, guy);
            if (pointer != null)
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
