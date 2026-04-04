package laboflieven.instructions.accinstructions;

import laboflieven.instructions.regular.VectorRegister;

import java.util.function.DoubleBinaryOperator;

/**
 * In-place element-wise binary ops on two vectors: for each index {@code i &lt; min(|L|,|R|)},
 * {@code L[i] = op(L[i], R[i])}. Shorter vector length limits how many elements are updated.
 */
final class VectorElementWiseBinary {

    private VectorElementWiseBinary() {
    }

    static void applyInPlaceLeft(VectorRegister leftVect, VectorRegister rightVect, DoubleBinaryOperator op) {
        var L = leftVect.value;
        var R = rightVect.value;
        int n = Math.min(L.size(), R.size());
        for (int i = 0; i < n; i++) {
            L.set(i, op.applyAsDouble(L.get(i), R.get(i)));
        }
    }
}
