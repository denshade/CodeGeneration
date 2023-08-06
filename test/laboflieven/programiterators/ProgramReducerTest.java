package laboflieven.programiterators;

import laboflieven.TestcaseInOutParameters;
import laboflieven.accinstructions.Inc;
import laboflieven.accinstructions.LoadAccLeftIntoRegister;
import laboflieven.challenges.ProgramTemplate;
import laboflieven.challenges.TestCases;
import laboflieven.examiners.MaxCostAccumulatorMatchAnyRegisterProgramFitnessExaminer;
import laboflieven.examiners.ProgramFitnessExaminerInterface;
import laboflieven.runners.AccStatementRunner;
import laboflieven.registers.Register;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProgramReducerTest {

    @Test
    void test()
    {
        List<TestcaseInOutParameters> collection = TestCases.getTestCases(new ProgramTemplate() {
            @Override
            public double run(double[] args) {
                return 2.0;
            }
        }, new double[][]{new double[]{1.0}}, 1);
        ProgramFitnessExaminerInterface evaluator = new MaxCostAccumulatorMatchAnyRegisterProgramFitnessExaminer(collection, new AccStatementRunner());
        List<Register> registers = Register.createRegisters(1);

        var reducer = new ProgramReducer(evaluator);
        var result = reducer.reduceAsFarAsPossible(List.of(new Inc(), new Inc(), new LoadAccLeftIntoRegister(registers.get(0)), new Inc(), new Inc()), registers);
        assertEquals(3, result.size());
    }

}