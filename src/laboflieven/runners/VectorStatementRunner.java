package laboflieven.runners;

import laboflieven.Program;

import java.util.List;
import java.util.Map;

/**
 * Runs a program with both scalar register values and vector register values
 * (e.g. accumulator vector registers used by {@link laboflieven.instructions.accinstructions.AccRegisterInstruction}).
 */
public interface VectorStatementRunner {

    /**
     * @param registerValues       name → scalar value for each {@link laboflieven.registers.Register} in the program
     * @param vectorRegisterValues name → vector for named vector registers (typically
     *                             {@link VectorAccStatementRunner#LEFT_ACC_NAME_VECTOR} and
     *                             {@link VectorAccStatementRunner#RIGHT_ACC_NAME_VECTOR}); missing keys default to empty vectors
     */
    VectorStatementRunResult execute(
            Program program,
            Map<String, Double> registerValues,
            Map<String, List<Double>> vectorRegisterValues
    );
}
