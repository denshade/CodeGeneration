package laboflieven.loggers;

import laboflieven.InstructionMark;
import laboflieven.common.InstructionOpcode;
import laboflieven.common.RegularInstructionOpcode;
import laboflieven.functional.loggers.InstructionIndexPair;
import laboflieven.instructions.regular.RegularInstructionOpcodeEnum;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CsvFileFitnessLogger implements FitnessLogger
{
    FileWriter writer;
    private final List<InstructionOpcode> opcodes;

    public CsvFileFitnessLogger(File file, List<InstructionOpcode> opcodes) throws IOException {
        writer = new FileWriter(file);
        this.opcodes = opcodes;
    }

    public CsvFileFitnessLogger(File file) throws IOException {
        this(file, Arrays.stream(RegularInstructionOpcodeEnum.values()).map(RegularInstructionOpcode::new).collect(Collectors.toList()));
    }

    @Override
    public void addFitness(List<InstructionMark> instructions, int nrInstruction, int nrRegisters, double error) {
        InstructionIndexPair pair = new InstructionIndexPair(instructions, nrRegisters, opcodes);
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
