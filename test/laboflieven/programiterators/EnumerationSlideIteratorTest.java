package laboflieven.programiterators;

import laboflieven.InOutParameters;
import laboflieven.accinstructions.AccInstructionOpcodeEnum;
import laboflieven.accinstructions.InstructionFactory;
import laboflieven.challenges.P2;
import laboflieven.challenges.TestCases;
import laboflieven.common.AccInstructionOpcode;
import laboflieven.examiners.ProgramFitnessExaminer;
import laboflieven.examiners.ProgramFitnessExaminerInterface;
import laboflieven.loggers.SysOutAccFitnessLogger;
import laboflieven.runners.AccStatementRunner;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EnumerationSlideIteratorTest {

    @Test
    void iterate() {
        int curMaxRegisters = 2;
        List<double[]> points = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            points.add(new double[] { i, Math.sqrt(5)});
        }
        List<InOutParameters> collection = TestCases.getTestCases(new P2(), points.toArray(new double[0][0]),curMaxRegisters);
        ProgramFitnessExaminerInterface evaluator = new ProgramFitnessExaminer(collection, new AccStatementRunner());
        evaluator.addListener(new SysOutAccFitnessLogger(10000));

        var iterator = new EnumerationSlideIterator(evaluator,
                AccInstructionOpcode.create(AccInstructionOpcodeEnum.values()),
                new InstructionFactory()
        );
        System.out.println(iterator.iterate(2, 100));
    }
}