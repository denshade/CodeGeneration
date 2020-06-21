package laboflieven.programiterators;

import laboflieven.AccProgramFitnessExaminer;
import laboflieven.InOutParameters;
import laboflieven.accinstructions.InstructionFactory;
import laboflieven.challenges.P1;
import laboflieven.challenges.TestCases;
import laboflieven.statements.Register;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SimulatedAnnealingIteratorTest {

    @Test
    void iterate() {
        int curMaxRegisters = 4;
        List<double[]> points = new ArrayList<>();
        for (int i = 1; i < 40; i++) {
            points.add(new double[] { i,3,5, 0});
        }
        List<InOutParameters> collection = TestCases.getTestCases(new P1(), points.toArray(new double[0][0]),curMaxRegisters);
        AccProgramFitnessExaminer evaluator = new AccProgramFitnessExaminer(collection);
        SimulatedAnnealingIterator it = new SimulatedAnnealingIterator(new InstructionFactory(), 10000, 1);
        it.iterate(100000, 10, Register.createRegisters(2, "R"), evaluator);

    }
    @Test
    void iterateNeighbour() {
        SimulatedAnnealingIterator it = new SimulatedAnnealingIterator(new InstructionFactory(), 10000, 1);
        assertTrue(it.probabilityFollowNeighbour(10, 1, 0.1) > 1);
        assertTrue(it.probabilityFollowNeighbour(1, 10, 100) < 1);
    }
}