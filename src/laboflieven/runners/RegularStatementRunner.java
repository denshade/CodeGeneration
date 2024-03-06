package laboflieven.runners;

import laboflieven.InstructionMark;
import laboflieven.Program;
import laboflieven.instructions.regular.DualRegisterInstruction;
import laboflieven.instructions.regular.Instruction;
import laboflieven.registers.Register;
import laboflieven.instructions.regular.SingleRegisterInstruction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lveeckha on 31/05/2015.
 */
public class RegularStatementRunner implements StatementRunner {

    public int MAXINSTRUCT = 100;
    public RegularStatementRunner()
    {
    }

    public RegularStatementRunner(int maxExec)
    {
         MAXINSTRUCT =  maxExec;
    }


    /**
     *
     * @param registerValues name => Value pairs.
     */
    @Override
    public Map<String, Double> execute(Program program, Map<String, Double> registerValues)  {
        program.initializeRegisters(registerValues);
        List<InstructionMark> instructions = program.getInstructions();
        if (false)checkIfBound(program, instructions);
        int ip = 0;
        int instructionsRun = 0;
        int size = instructions.size();
        while ( ip < size)
        {
            instructionsRun++;
            if (instructionsRun > MAXINSTRUCT)
                break;
            Instruction instruction = (Instruction)instructions.get(ip);

            Integer pointer = instruction.execute(ip);
            if (pointer != null && pointer >= 0)
            {
                ip = pointer;
            }
            else
            {
                ip++;
            }
        }
        Map<String, Double> m = getResultValueMap(program);
        return m;
    }

    private Map<String, Double> getResultValueMap(Program program) {
        Map<String, Double> m = new HashMap<>();
        for (Register registr : program.getRegisters())
        {
            m.put(registr.name, registr.value);
        }
        return m;
    }

    private void checkIfBound(Program program, List<InstructionMark> instructions) {
        for (InstructionMark instr : instructions)
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