package laboflieven.loggers;

import laboflieven.InstructionMark;
import laboflieven.accinstructions.AccInstructionOpcodeEnum;
import laboflieven.accinstructions.AccRegisterInstruction;
import laboflieven.functional.loggers.InstructionIndexPair;
import laboflieven.functional.loggers.InstructionsBigIntegerIndex;
import laboflieven.functional.loggers.RegistersBigIntegerIndex;
import laboflieven.statements.RegularInstructionOpcodeEnum;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

public class CsvFileFitnessLogger implements FitnessLogger
{
    FileWriter writer;
    public CsvFileFitnessLogger(File file) throws IOException {
        writer = new FileWriter(file);

    }

    @Override
    public void addFitness(List<InstructionMark> instructions, int nrInstruction, int nrRegisters, double error) {
        InstructionIndexPair pair = new InstructionIndexPair(instructions, nrInstruction, nrRegisters);
        try{
            writer.write(pair.getX().toString()+";"+pair.getY().toString() + ";"+error+"\n");
        } catch (Exception e )
        {
            System.out.println(e);
        }
    }

    public void finish() throws IOException {
        writer.close();
    }

}
