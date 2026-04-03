package laboflieven.runners;

import laboflieven.VectorProgram;
import laboflieven.instructions.accinstructions.LoadVectorSumIntoLeft;
import laboflieven.instructions.regular.VectorRegister;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VectorAccStatementRunnerTest {

    @Test
    void initialVectorStateIsVisibleToInstructions() {
        List<VectorRegister> regs = VectorRegister.createRegisters(1);
        var sumIntoLeft = new LoadVectorSumIntoLeft();
        var runner = new VectorAccStatementRunner();
        VectorProgram p = new VectorProgram(List.of(sumIntoLeft), regs);

        VectorStatementRunResult out = runner.execute(
                p,
                Map.of(
                        "R1", List.of(0.0),
                        VectorAccStatementRunner.LEFT_ACC_NAME_VECTOR, List.of(3.0, 4.0, 5.0)));

        assertEquals(List.of(12.0), out.vectors().get("AL"));
        assertEquals(List.of(3.0, 4.0, 5.0), out.vectors().get(VectorAccStatementRunner.LEFT_ACC_NAME_VECTOR));
        assertEquals(List.of(0.0), out.vectors().get("R1"));
    }

    @Test
    void emptyInitialVectors() {
        List<VectorRegister> regs = VectorRegister.createRegisters(1);
        var sumIntoLeft = new LoadVectorSumIntoLeft();
        var runner = new VectorAccStatementRunner();
        VectorProgram p = new VectorProgram(List.of(sumIntoLeft), regs);

        VectorStatementRunResult out = runner.execute(p, Map.of("R1", List.of(0.0)));

        assertEquals(List.of(0.0), out.vectors().get("AL"));
    }
}
