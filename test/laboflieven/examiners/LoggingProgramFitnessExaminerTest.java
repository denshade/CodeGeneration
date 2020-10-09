package laboflieven.examiners;

import laboflieven.InOutParameters;
import laboflieven.challenges.Max;
import laboflieven.challenges.TestCases;
import laboflieven.runners.AccStatementRunner;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LoggingProgramFitnessExaminerTest {

    @Test
    void calculateFitness() throws IOException {
        List<double[]> points = new ArrayList<>();
        points.add(new double[] { 1, 10});
        points.add(new double[] { 10, 10});
        points.add(new double[] { 100, 10});
        List<InOutParameters> collection = TestCases.getTestCases(new Max(), points.toArray(new double[0][0]),2);
        File tempFile = File.createTempFile("pref", "suf");
        LoggingProgramFitnessExaminer log = new LoggingProgramFitnessExaminer(tempFile,collection,new AccStatementRunner() );
        log.calculateFitness(List.of(), List.of());
        log.writeAndClose();
        List<String> s = Files.readAllLines(tempFile.toPath());
        assertTrue(s.size() > 0);
        tempFile.delete();
    }
}