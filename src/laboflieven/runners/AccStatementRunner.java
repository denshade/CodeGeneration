package laboflieven.runners;

import laboflieven.InstructionMark;
import laboflieven.Program;
import laboflieven.accinstructions.AccRegisterInstruction;
import laboflieven.statements.Register;
import laboflieven.statements.VectorRegister;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by lveeckha on 31/05/2015.
 */
public class AccStatementRunner implements StatementRunner {

    public static final String LEFT_ACC_NAME = "AL";
    public static final String RIGHT_ACC_NAME = "AR";
    public static final String LEFT_ACC_NAME_VECTOR = "ALV";
    public static final String RIGHT_ACC_NAME_VECTOR = "ARV";
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
        if (verbose){
            System.out.println("__________");
            System.out.println("INIT");
            System.out.println("__________");
        }
        program.initializeRegisters(registerValues);
        List<InstructionMark> instructions = program.getInstructions();
        Register left = new Register(LEFT_ACC_NAME);
        Register right = new Register(RIGHT_ACC_NAME);
        VectorRegister leftVector = new VectorRegister(LEFT_ACC_NAME_VECTOR);
        VectorRegister rightVector = new VectorRegister(RIGHT_ACC_NAME_VECTOR);
        boolean instructionOverflow = false;
        int ip = 0;
        int instructionsRun = 0;
        int size = instructions.size();
        while ( ip < size)
        {
            instructionsRun++;
            if (instructionsRun > MAXINSTRUCT)
            {
                instructionOverflow= true;
                break;
            }
            AccRegisterInstruction instruction = (AccRegisterInstruction) instructions.get(ip);
            if (verbose) {
                System.out.println(instruction);
            }
            Integer pointer = instruction.execute(left, right, leftVector, rightVector, ip);
            if (verbose) {
                System.out.print("Pointer:" + pointer+" ");
                System.out.print(program.getRegisters().stream().map(e -> e.name+ "="+ e.value+ ",").collect(Collectors.joining()));
                System.out.print("left " + left.value + " right " + right.value);
                System.out.println();
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
        Map<String, Double> m ;
        if (instructionOverflow) {
            m = getResultNanValueMap(program);
            m.put(left.name, Double.NaN);
            m.put(right.name, Double.NaN);
        } else {
            m = getResultValueMap(program);
            m.put(left.name, left.value);
            m.put(right.name, right.value);
        }
        return m;
    }

    private Map<String, Double> getResultValueMap(Program program) {
        Map<String, Double> m = new HashMap<>(program.getRegisters().size());
        for (Register registr : program.getRegisters())
        {
            m.put(registr.name, registr.value);
        }
        return m;
    }
    private Map<String, Double> getResultNanValueMap(Program program) {
        Map<String, Double> m = new HashMap<>(program.getRegisters().size());
        for (Register registr : program.getRegisters())
        {
            m.put(registr.name, Double.NaN);
        }
        return m;
    }
}