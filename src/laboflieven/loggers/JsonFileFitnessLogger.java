package laboflieven.loggers;

import laboflieven.InstructionMark;
import laboflieven.accinstructions.AccInstructionOpcodeEnum;
import laboflieven.accinstructions.AccRegisterInstruction;
import laboflieven.common.AccInstructionOpcode;
import laboflieven.common.RegularInstructionOpcode;
import laboflieven.statements.DualRegisterInstruction;
import laboflieven.statements.SingleRegisterInstruction;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

public class JsonFileFitnessLogger implements FitnessLogger
{
    FileWriter writer;
    public JsonFileFitnessLogger(File file) throws IOException {
        writer = new FileWriter(file);

    }

    @Override
    public void addFitness(List<InstructionMark> instructions, int nrInstruction, int nrRegisters, double error) {

    }

    public void finish() throws IOException {
        writer.close();
    }

}
