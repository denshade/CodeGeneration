package laboflieven;

import laboflieven.challenges.TestCases;
import laboflieven.examiners.AccumulatorProgramFitnessExaminer;
import laboflieven.examiners.ProgramFitnessExaminerInterface;
import laboflieven.registers.NumberNamingScheme;
import laboflieven.runners.AccStatementRunner;
import laboflieven.registers.Register;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class TestData {
    public static void main(String[] args) throws IOException {
        AccStatementRunner runner = new AccStatementRunner();

        String s = "[left++, R1 = left, R2 = left, R2 = left, R2 = right,  left = R1]";
        var k = AccProgramParser.parse(s);
        List<TestcaseInOutParameters> conditions = new TestCases().loadFromCsvFile(new File("C:\\temp\\slingersummary.csv"));
        System.out.println("Conditions");
        System.out.println(conditions);
        ProgramFitnessExaminerInterface evaluator = new AccumulatorProgramFitnessExaminer(
                conditions, runner,
                "R1");
        System.out.println(evaluator.evaluateDifference(new Program(k, new NumberNamingScheme().createRegisters(2))));
    }
}
