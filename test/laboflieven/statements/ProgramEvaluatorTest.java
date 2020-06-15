package laboflieven;

import junit.framework.TestCase;
import laboflieven.statements.Add;
import laboflieven.statements.Instruction;
import laboflieven.statements.Register;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Lieven on 28/06/2015.
 */
public class ProgramEvaluatorTest extends TestCase {

    public void testEvaluateTrue() throws Exception {
        List<InOutParameters> parameters = new ArrayList<>();
        InOutParameters parameter1 = new InOutParameters();
        parameter1.input.put("R1", 1.0);
        parameter1.expectedOutput.put("R1", 2.0);
        parameters.add(parameter1);
        ProgramFitnessExaminer ev = new ProgramFitnessExaminer(parameters);

        Register register1 = new Register("R1");

        List<InstructionMark> instructions = new ArrayList<>();
        instructions.add(new Add(register1, register1));
        assertTrue(ev.isFit(instructions, Collections.singletonList(register1)));

    }
    public void testEvaluateFalse() throws Exception {
        List<InOutParameters> parameters = new ArrayList<>();
        InOutParameters parameter1 = new InOutParameters();
        parameter1.input.put("R1", 1.0);
        parameter1.expectedOutput.put("R1", 1.0);
        parameters.add(parameter1);
        ProgramFitnessExaminer ev = new ProgramFitnessExaminer(parameters);

        Register register1 = new Register("R1");

        List<InstructionMark> instructions = new ArrayList<>();
        instructions.add(new Add(register1, register1));
        assertFalse(ev.isFit(instructions, Collections.singletonList(register1)));

    }

}