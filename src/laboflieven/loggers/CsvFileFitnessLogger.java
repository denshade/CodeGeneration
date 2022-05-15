package laboflieven.loggers;

import laboflieven.InstructionMark;
import laboflieven.functional.loggers.InstructionIndexPair;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CsvFileFitnessLogger implements FitnessLogger
{
    FileWriter writer;
    public CsvFileFitnessLogger(File file) throws IOException {
        writer = new FileWriter(file);

    }

    @Override
    public void addFitness(List<InstructionMark> instructions, int nrInstruction, int nrRegisters, double error) {
        InstructionIndexPair pair = new InstructionIndexPair(instructions, nrRegisters, InstructionIndexPair.getDefaultOpcodes(instructions));
        try{
            writer.write(pair.getX().toString()+";"+pair.getY().toString() + ";"+error+"\n");
        } catch (Exception e )
        {
            throw new RuntimeException(e);
        }
    }

    public void finish() throws IOException {
        writer.close();
    }

}
