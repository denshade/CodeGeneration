package laboflieven.instructions.accinstructions;

import laboflieven.common.InstructionOpcode;
import laboflieven.instructions.regular.VectorRegister;
import laboflieven.registers.Register;

import java.util.Vector;

/**
 * Replaces the left vector with {@code [1, 2, …, n]} where {@code n} is taken from the scalar
 * left accumulator (floored); non-positive or non-finite {@code left} yields an empty vector.
 */
public class LeftVectFillSequence extends NoRegisterInstruction {

    /** Avoid accidental huge allocations from bad evolved programs. */
    private static final int MAX_N = 1_000_000;

    @Override
    public InstructionOpcode getInstructionOpcode() {
        return new laboflieven.common.AccInstructionOpcode(AccInstructionOpcodeEnum.LeftVectFillSequence);
    }

    @Override
    public Integer execute(Register left, Register right, int ip) {
        return null;
    }

    @Override
    public Integer execute(Register left, Register right, VectorRegister leftVect, VectorRegister rightVect, int ip) {
        Vector<Double> v = leftVect.value;
        v.clear();
        double lv = left.value;
        if (Double.isNaN(lv) || Double.isInfinite(lv) || lv <= 0) {
            return null;
        }
        int n = (int) Math.floor(lv);
        if (n <= 0) {
            return null;
        }
        n = Math.min(n, MAX_N);
        v.ensureCapacity(n);
        for (int i = 1; i <= n; i++) {
            v.add((double) i);
        }
        return null;
    }

    @Override
    public String toString() {
        return "vectleft = 1..left";
    }
}
