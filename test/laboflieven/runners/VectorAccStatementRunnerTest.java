package laboflieven.runners;

import laboflieven.Program;
import laboflieven.instructions.accinstructions.LoadVectorSumIntoLeft;
import laboflieven.registers.Register;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VectorAccStatementRunnerTest {

    @Test
    void initialVectorStateIsVisibleToInstructions() {
        var r1 = new Register("R1");
        var sumIntoLeft = new LoadVectorSumIntoLeft();
        var runner = new VectorAccStatementRunner();
        Program p = new Program(List.of(sumIntoLeft), List.of(r1));

        VectorStatementRunResult out = runner.execute(
                p,
                Map.of("R1", 0.0),
                Map.of(VectorAccStatementRunner.LEFT_ACC_NAME_VECTOR, List.of(3.0, 4.0, 5.0)));

        assertEquals(12.0, out.scalars().get("AL"), 1e-9);
        assertEquals(List.of(3.0, 4.0, 5.0), out.vectors().get(VectorAccStatementRunner.LEFT_ACC_NAME_VECTOR));
    }

    @Test
    void statementRunnerInterfaceUsesEmptyInitialVectors() {
        var r1 = new Register("R1");
        var sumIntoLeft = new LoadVectorSumIntoLeft();
        var runner = new VectorAccStatementRunner();
        Program p = new Program(List.of(sumIntoLeft), List.of(r1));

        Map<String, Double> scalars = runner.execute(p, Map.of("R1", 0.0));

        assertEquals(0.0, scalars.get("AL"), 1e-9);
    }
}
