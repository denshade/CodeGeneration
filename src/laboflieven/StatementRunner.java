package laboflieven;

import laboflieven.statements.DualRegisterInstruction;
import laboflieven.statements.Instruction;
import laboflieven.statements.Register;
import laboflieven.statements.SingleRegisterInstruction;

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
    public void execute(Program program, Map<String, Double> registerValues)  {
        program.initializeRegisters(registerValues);
        List<Instruction> instructions = program.getInstructions();
        if (false)checkIfBound(program, instructions);
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

    private void checkIfBound(Program program, List<Instruction> instructions) {
        for (Instruction instr : instructions)
        {
            Register toFind = null;
            if (instr instanceof SingleRegisterInstruction){
                toFind = ((SingleRegisterInstruction) instr).destination;
            } else if (instr instanceof DualRegisterInstruction){
                toFind = ((DualRegisterInstruction) instr).destination;
            }

            boolean found = false;
            for (Register reg : program.getRegisters()) {
                if (reg.equals(toFind)) found = true;
            }
            if (!found) {
                throw new RuntimeException("Registers not bound! ");
            }
        }
    }
}