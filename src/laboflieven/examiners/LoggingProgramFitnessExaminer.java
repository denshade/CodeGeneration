package laboflieven.examiners;

import laboflieven.InOutParameters;
import laboflieven.InstructionMark;
import laboflieven.ProgramEnumerator;
import laboflieven.runners.StatementRunner;
import laboflieven.statements.Register;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Lieven on 14/06/2015.
 */
public class LoggingProgramFitnessExaminer extends ProgramFitnessExaminer
{
    private final Map<BigInteger, Double>  errors = new HashMap<>();
    private final FileWriter writer;
    /**
     * @param conditions Conditions that define the input parameters & the expected outcome.
     */
    public LoggingProgramFitnessExaminer(File valueFile, List<InOutParameters> conditions, StatementRunner runner) throws IOException {
        super(conditions, runner);
        writer = new FileWriter(valueFile);
    }

    public double calculateFitness(List<InstructionMark> instructions, List<Register> registers)
    {
        ProgramEnumerator enumerator = new ProgramEnumerator(List.of(), registers.size());
        double err = super.calculateFitness(instructions, registers);
        var key = enumerator.convert(instructions);
        if (errors.containsKey(key) && errors.get(key) > err) {
            errors.put(key, err);
        }
        errors.putIfAbsent(key, err);
        return err;
    }

    public void writeAndClose() throws Exception {
        for (Map.Entry<BigInteger, Double> e : errors.entrySet()) {
            String s = e.getKey() + "," + e.getValue() + "\n";
            writer.write(s);
        }
        writer.close();
    }

}
