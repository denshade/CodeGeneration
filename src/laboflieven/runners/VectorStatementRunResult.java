package laboflieven.runners;

import java.util.List;
import java.util.Map;

/**
 * Result of running a program with scalar {@link laboflieven.registers.Register} values
 * and vector {@link laboflieven.instructions.regular.VectorRegister} state.
 */
public record VectorStatementRunResult(
        Map<String, Double> scalars,
        Map<String, List<Double>> vectors
) {
}
