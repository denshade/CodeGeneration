package laboflieven.loggers;

import laboflieven.InstructionMark;
import laboflieven.accinstructions.AccInstructionOpcodeEnum;
import laboflieven.accinstructions.AccRegisterInstruction;
import laboflieven.common.InstructionOpcode;
import laboflieven.common.RegularInstructionOpcode;
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
        BigInteger[] numbers = getXandY(instructions, nrInstruction, nrRegisters);
        try{
            writer.write(numbers[0].toString()+";"+numbers[1].toString() + ";"+error+"\n");
        } catch (Exception e )
        {
            System.out.println(e.toString());
        }
    }

    public void finish() throws IOException {
        writer.close();
    }


    public BigInteger[] getXandY(List<InstructionMark> instructions, int nrInstruction, int nrRegisters)
    {
        List opcodes;
        if (instructions.get(0) instanceof AccRegisterInstruction) {
            opcodes = List.of(AccInstructionOpcodeEnum.values());
        } else {
            opcodes = List.of(RegularInstructionOpcodeEnum.values());
        }
        return new BigInteger[]{new InstructionsBigIntegerIndex(instructions, opcodes).getSumRegister(), new RegistersBigIntegerIndex(instructions, nrRegisters).getSumRegister()};
    }


}
