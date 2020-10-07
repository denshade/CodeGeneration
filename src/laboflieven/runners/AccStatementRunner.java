package laboflieven.runners;

import laboflieven.InstructionMark;
import laboflieven.Program;
import laboflieven.accinstructions.AccRegisterInstruction;
import laboflieven.statements.Register;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lveeckha on 31/05/2015.
 */
public class AccStatementRunner implements StatementRunner {

    public int MAXINSTRUCT;
    public boolean verbose = false;

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
    public Map<String, Double> execute(Program program, Map<String, Double> registerValues)
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
            if (verbose) {
                System.out.println(instruction);
            }
            Integer pointer = instruction.execute(left, right, ip);
            if (verbose) {
                System.out.println(pointer);
                System.out.println(registerValues);
            }

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
        m.put(left.name, left.value);
        m.put(right.name, right.value);
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
}