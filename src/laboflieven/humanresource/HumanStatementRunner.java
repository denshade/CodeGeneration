package laboflieven.humanresource;

import laboflieven.humanresource.instructions.Inbox;
import laboflieven.humanresource.model.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * Created by lveeckha on 31/05/2015.
 */
public class HumanStatementRunner {

    public int MAXINSTRUCT = 100;
    private final Map<String, Integer> initialRegisterValues;


    public HumanStatementRunner()
    {
        initialRegisterValues= new HashMap<>();
    }

    public HumanStatementRunner(int maxInstructions)
    {
        MAXINSTRUCT = maxInstructions;
        initialRegisterValues= new HashMap<>();
    }

    public HumanStatementRunner(int maxInstructions, Map<String, Integer> initialRegisterValues)
    {
        MAXINSTRUCT = maxInstructions;
        this.initialRegisterValues = initialRegisterValues;
    }


    /**
     *
     */
    public void execute(HumanResourceProgram program, Queue<Integer> incomingQ, Queue<Integer> outgoingQ) throws InvalidProgramException
    {
        Guy guy = new Guy();
        int instructionsRun = 0;
        int ip = 0;
        final List<HumanInstruction> instructions = program.getInstructions();
        initializeRegisters(program);
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

    private void initializeRegisters(HumanResourceProgram program)
    {
      for (HumanRegister reg : program.getRegisters()) {
          if (initialRegisterValues.containsKey(reg.name)) {
              reg.value = initialRegisterValues.get(reg.name);
          }
      }
    }
}
