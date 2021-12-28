package laboflieven.programiterators;

import laboflieven.TestcaseInOutParameters;
import laboflieven.accinstructions.InstructionFactory;
import laboflieven.challenges.P1;
import laboflieven.challenges.TestCases;
import laboflieven.examiners.ProgramFitnessExaminer;
import laboflieven.examiners.ProgramFitnessExaminerInterface;
import laboflieven.functional.programiterators.SimulatedAnnealingFollowNeighbourProbability;
import laboflieven.runners.AccStatementRunner;
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
        List<TestcaseInOutParameters> collection = TestCases.getTestCases(new P1(), points.toArray(new double[0][0]),curMaxRegisters);
        ProgramFitnessExaminerInterface evaluator = new ProgramFitnessExaminer(collection,new AccStatementRunner());
        SimulatedAnnealingIterator it = new SimulatedAnnealingIterator(new InstructionFactory(), 10000, 1);
        it.iterate(100000, 10, Register.createRegisters(2, "R"), evaluator);

    }
    @Test
    void iterateNeighbour() {
        assertTrue(SimulatedAnnealingFollowNeighbourProbability.probabilityFollowNeighbour(10, 1, 0.1, 10000) > 1);
        assertTrue(SimulatedAnnealingFollowNeighbourProbability.probabilityFollowNeighbour(1, 10, 100, 10000) < 1);
    }
}