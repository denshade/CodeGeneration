package laboflieven.runners;

import laboflieven.VectorProgram;

import java.util.List;
import java.util.Map;

/**
 * Runs a {@link VectorProgram} (vector registers only for program storage). The result is
 * {@link VectorStatementRunResult#vectors()} — full vectors for each program register and for
 * {@link VectorAccStatementRunner#LEFT_ACC_NAME_VECTOR} / {@link VectorAccStatementRunner#RIGHT_ACC_NAME_VECTOR},
 * plus length-1 lists for scalar accumulators {@code AL} / {@code AR}.
 */
public interface VectorStatementRunner {

    /**
     * @param vectorRegisterValues name → initial vector; program registers use the same keys as
     *                             {@link laboflieven.instructions.regular.VectorRegister#getName()}.
     *                             Accumulator vectors use {@link VectorAccStatementRunner#LEFT_ACC_NAME_VECTOR}
     *                             and {@link VectorAccStatementRunner#RIGHT_ACC_NAME_VECTOR}. Missing keys default to empty vectors.
     */
    VectorStatementRunResult execute(VectorProgram program, Map<String, List<Double>> vectorRegisterValues);
}
