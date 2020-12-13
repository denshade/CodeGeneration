package laboflieven.genericsolutions;

import laboflieven.challenges.TestcaseSource;
import laboflieven.challenges.XorFinder;
import laboflieven.common.Configuration;
import laboflieven.examiners.ProgramFitnessExaminer;
import laboflieven.runners.AccStatementRunner;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnumerationProgramFinderTest {

    @Test
    void findSolutions() {
        TestcaseSource source = new XorFinder();
        var  selector = new EnumerationProgramFinder();
        var evaluator = new ProgramFitnessExaminer(source.getTestCases(), new AccStatementRunner());
        var configuration = new Configuration();
        configuration.setFitnessExaminer(evaluator);
        var prog = selector.findSolutions(configuration);
        assertNotNull(prog);
    }

}