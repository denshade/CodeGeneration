package laboflieven.examiners;

import laboflieven.InstructionMark;
import laboflieven.TestcaseInOutParameters;
import laboflieven.accinstructions.*;
import laboflieven.challenges.RoundFinder;
import laboflieven.challenges.TestCases;
import laboflieven.runners.AccStatementRunner;
import laboflieven.statements.Register;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AccumulatorMatchAnyRegisterProgramFitnessExaminerTest {

    @Test
    void test()
    {
        List<TestcaseInOutParameters> collection = TestCases.getTestCases(new RoundFinder(), TestCases.getExampleInput1D(Math.PI*2,0.01), 1);
        AccStatementRunner runner = new AccStatementRunner();
        runner.verbose = true;
        var a = new AccumulatorMatchAnyRegisterProgramFitnessExaminer(collection, runner);
        var registers = Register.createRegisters(1);
        Register register0 = registers.get(0);
        List<InstructionMark> instructions = List.of(new PI(), new Swap(), new Inc(), new Inc(), new Swap(), new Div(),
                new LoadIntoRightAcc(register0), new Sub(), new Cos(), new LoadAccLeftIntoRegister(register0));
        assertEquals(0.1, a.calculateFitness(instructions, Register.createRegisters(1)));
    }

}