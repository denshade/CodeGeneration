package laboflieven;

import laboflieven.statements.Instruction;
import laboflieven.statements.Register;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by Lieven on 14/06/2015.
 */
public class LoggingProgramFitnessExaminer extends ProgramFitnessExaminer
{
    private final FileWriter writer;
    /**
     * @param conditions Conditions that define the input parameters & the expected outcome.
     */
    public LoggingProgramFitnessExaminer(File valueFile, List<InOutParameters> conditions) throws IOException {
        super(conditions);
        writer = new FileWriter(valueFile);
    }

    public double calculateFitness(List<Instruction> instructions, List<Register> registers)
    {
        double err = super.calculateFitness(instructions, registers);
        try {
            writer.write(err+"," + instructions.size()+","+instructions.toString()+"\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return err;
    }

}
