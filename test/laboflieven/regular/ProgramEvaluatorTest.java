package laboflieven.regular;

import laboflieven.InstructionMark;
import laboflieven.TestcaseInOutParameters;
import laboflieven.examiners.ProgramFitnessExaminer;
import laboflieven.examiners.ProgramFitnessExaminerInterface;
import laboflieven.instructions.regular.Add;
import laboflieven.runners.RegularStatementRunner;
import laboflieven.registers.Register;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by Lieven on 28/06/2015.
 */
public class ProgramEvaluatorTest {

    public void testEvaluateTrue() throws Exception {
        List<TestcaseInOutParameters> parameters = new ArrayList<>();
        TestcaseInOutParameters parameter1 = new TestcaseInOutParameters();
        parameter1.input.put("R1", 1.0);
        parameter1.expectedOutput.put("R1", 2.0);
        parameters.add(parameter1);
        ProgramFitnessExaminerInterface ev = new ProgramFitnessExaminer(parameters, new RegularStatementRunner());

        Register register1 = new Register("R1");

        List<InstructionMark> instructions = new ArrayList<>();
        instructions.add(new Add(register1, register1));
        assertTrue(ev.isFit(instructions, Collections.singletonList(register1)));

    }
    public void testEvaluateFalse() throws Exception {
        List<TestcaseInOutParameters> parameters = new ArrayList<>();
        TestcaseInOutParameters parameter1 = new TestcaseInOutParameters();
        parameter1.input.put("R1", 1.0);
        parameter1.expectedOutput.put("R1", 1.0);
        parameters.add(parameter1);
        ProgramFitnessExaminerInterface ev = new ProgramFitnessExaminer(parameters, new RegularStatementRunner());

        Register register1 = new Register("R1");

        List<InstructionMark> instructions = new ArrayList<>();
        instructions.add(new Add(register1, register1));
        assertFalse(ev.isFit(instructions, Collections.singletonList(register1)));

    }

}