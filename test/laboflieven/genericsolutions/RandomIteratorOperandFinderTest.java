package laboflieven.genericsolutions;

import laboflieven.TestcaseInOutParameters;
import laboflieven.challenges.TestCases;
import laboflieven.common.Configuration;
import laboflieven.examiners.AccumulatorMatchAnyRegisterProgramFitnessExaminer;
import laboflieven.examiners.ProgramFitnessExaminerInterface;
import laboflieven.runners.AccStatementRunner;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RandomIteratorOperandFinderTest {

    @Test
    void find() {
        List<TestcaseInOutParameters> collection = new ArrayList<>();
        collection.add(createParameter(2.0, 2.0));
        collection.add(createParameter(-15.0, 15.0));
        collection.add(createParameter(0.0, 0.0));
        var finder = new RandomIteratorOperandFinder();
        ProgramFitnessExaminerInterface evaluator = new AccumulatorMatchAnyRegisterProgramFitnessExaminer(collection, new AccStatementRunner());
        var config = new Configuration();
        config.setNumberOfRegisters(1);
        config.setFitnessExaminer(evaluator);
        config.setMaxNrInstructions(4);
        //config.setStopAtSolution(true);

        var operands = finder.find(config);
        assertEquals(4, operands.size());
    }

    private static TestcaseInOutParameters createParameter(double a, double result)
    {
        return TestcaseInOutParameters.createParameter(new double[]{a}, result, 1);
    }
}