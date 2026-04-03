package laboflieven.runners;

import java.util.List;
import java.util.Map;

/**
 * Result of a {@link laboflieven.VectorProgram} run: program {@link laboflieven.instructions.regular.VectorRegister}
 * contents as lists, plus accumulator rows (length-1 lists for {@code AL}/{@code AR}, full lists for {@code ALV}/{@code ARV}).
 */
public record VectorStatementRunResult(Map<String, List<Double>> vectors) {
}
