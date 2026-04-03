package laboflieven.runners;

import laboflieven.InstructionMark;
import laboflieven.Program;
import laboflieven.instructions.accinstructions.AccRegisterInstruction;
import laboflieven.instructions.accinstructions.JumpInstruction;
import laboflieven.instructions.regular.VectorRegister;
import laboflieven.registers.Register;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Like {@link AccStatementRunner}, but initial and final state of the left/right vector accumulators
 * ({@link #LEFT_ACC_NAME_VECTOR}, {@link #RIGHT_ACC_NAME_VECTOR}) are passed in and returned as vectors.
 */
public class VectorAccStatementRunner implements VectorStatementRunner, StatementRunner {

    public static final String LEFT_ACC_NAME = AccStatementRunner.LEFT_ACC_NAME;
    public static final String RIGHT_ACC_NAME = AccStatementRunner.RIGHT_ACC_NAME;
    public static final String JUMP_ACC_NAME = AccStatementRunner.JUMP_ACC_NAME;

    public static final String LEFT_ACC_NAME_VECTOR = AccStatementRunner.LEFT_ACC_NAME_VECTOR;
    public static final String RIGHT_ACC_NAME_VECTOR = AccStatementRunner.RIGHT_ACC_NAME_VECTOR;

    public int MAXINSTRUCT;
    public boolean verbose = false;

    public VectorAccStatementRunner() {
        this(100);
    }

    public VectorAccStatementRunner(int maxExec) {
        MAXINSTRUCT = maxExec;
    }

    /**
     * Scalar-only execution: vector accumulators start empty (same idea as {@link AccStatementRunner}).
     */
    @Override
    public Map<String, Double> execute(Program program, Map<String, Double> registerValues) {
        return execute(program, registerValues, Map.of()).scalars();
    }

    @Override
    public VectorStatementRunResult execute(
            Program program,
            Map<String, Double> registerValues,
            Map<String, List<Double>> vectorRegisterValues) {
        if (verbose) {
            System.out.println("__________");
            System.out.println("INIT");
            System.out.println("__________");
        }
        program.initializeRegisters(registerValues);
        List<InstructionMark> instructions = program.getInstructions();
        Register left = new Register(LEFT_ACC_NAME);
        Register right = new Register(RIGHT_ACC_NAME);
        Register jump = new Register(JUMP_ACC_NAME, 2);

        VectorRegister leftVector = new VectorRegister(LEFT_ACC_NAME_VECTOR);
        VectorRegister rightVector = new VectorRegister(RIGHT_ACC_NAME_VECTOR);
        fillVector(leftVector, vectorRegisterValues.get(LEFT_ACC_NAME_VECTOR));
        fillVector(rightVector, vectorRegisterValues.get(RIGHT_ACC_NAME_VECTOR));

        boolean instructionOverflow = false;
        int ip = 0;
        int instructionsRun = 0;
        int size = instructions.size();
        while (ip < size) {
            instructionsRun++;
            if (instructionsRun > MAXINSTRUCT) {
                instructionOverflow = true;
                break;
            }
            InstructionMark instruction = instructions.get(ip);
            if (verbose) {
                System.out.println(instruction);
            }
            Integer pointer;
            if (instruction instanceof JumpInstruction) {
                pointer = ((JumpInstruction) instruction).execute(left, right, jump, ip);
            } else if (instruction instanceof AccRegisterInstruction) {
                pointer = ((AccRegisterInstruction) instruction).execute(left, right, leftVector, rightVector, ip);
            } else {
                throw new IllegalArgumentException("Unsupported instruction" + instruction);
            }
            if (verbose) {
                System.out.print("Pointer:" + pointer + " ");
                System.out.print(program.getRegisters().stream().map(e -> e.name + "=" + e.value + ",").collect(Collectors.joining()));
                System.out.print("left " + left.value + " right " + right.value);
                System.out.println();
            }

            if (pointer != null && pointer >= 0) {
                ip = pointer;
            } else {
                ip++;
            }
        }
        if (instructionOverflow) {
            return new VectorStatementRunResult(NanMap.instance(), Map.of());
        }
        Map<String, Double> scalars = getScalarResultMap(program, left, right);
        Map<String, List<Double>> vectors = getVectorResultMap(leftVector, rightVector);
        return new VectorStatementRunResult(scalars, vectors);
    }

    private static void fillVector(VectorRegister reg, List<Double> values) {
        reg.value.clear();
        if (values != null) {
            reg.value.addAll(values);
        }
    }

    private Map<String, Double> getScalarResultMap(Program program, Register left, Register right) {
        Map<String, Double> m = new HashMap<>(program.getRegisters().size() + 2);
        for (Register registr : program.getRegisters()) {
            m.put(registr.name, registr.value);
        }
        m.put(left.name, left.value);
        m.put(right.name, right.value);
        return m;
    }

    private static Map<String, List<Double>> getVectorResultMap(VectorRegister left, VectorRegister right) {
        Map<String, List<Double>> m = new HashMap<>(2);
        m.put(left.name, new ArrayList<>(left.value));
        m.put(right.name, new ArrayList<>(right.value));
        return m;
    }
}
