package laboflieven.runners;

import laboflieven.InstructionMark;
import laboflieven.Program;
import laboflieven.VectorProgram;
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
 * Like {@link AccStatementRunner}, but program state lives in {@link VectorProgram} / {@link VectorRegister}
 * only. Scalar {@link Register} instances exist only as a binding layer for existing instructions
 * (first element of each program vector register).
 */
public class VectorAccStatementRunner implements VectorStatementRunner {

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

    @Override
    public VectorStatementRunResult execute(VectorProgram vectorProgram, Map<String, List<Double>> vectorRegisterValues) {
        if (verbose) {
            System.out.println("__________");
            System.out.println("INIT");
            System.out.println("__________");
        }
        vectorProgram.initializeVectors(vectorRegisterValues);

        List<Register> boundScalars = vectorProgram.createScalarRegistersForBinding();
        Program program = new Program(vectorProgram.getInstructions(), boundScalars);
        program.initializeRegisters(vectorProgram.firstElementsAsScalarMap());

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
            return new VectorStatementRunResult(Map.of());
        }
        vectorProgram.writeFirstElementsFromRegisters(program.getRegisters());
        return new VectorStatementRunResult(buildOutputVectors(vectorProgram, left, right, leftVector, rightVector));
    }

    private static Map<String, List<Double>> buildOutputVectors(
            VectorProgram vectorProgram,
            Register left,
            Register right,
            VectorRegister leftVector,
            VectorRegister rightVector) {
        Map<String, List<Double>> m = new HashMap<>(vectorProgram.getRegisters().size() + 4);
        for (VectorRegister vr : vectorProgram.getRegisters()) {
            m.put(vr.name, new ArrayList<>(vr.value));
        }
        m.put(left.name, List.of(left.value));
        m.put(right.name, List.of(right.value));
        m.put(leftVector.name, new ArrayList<>(leftVector.value));
        m.put(rightVector.name, new ArrayList<>(rightVector.value));
        return m;
    }

    private static void fillVector(VectorRegister reg, List<Double> values) {
        reg.value.clear();
        if (values != null) {
            reg.value.addAll(values);
        }
    }
}
