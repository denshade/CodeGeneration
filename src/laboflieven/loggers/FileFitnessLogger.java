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

public class FileFitnessLogger implements FitnessLogger
{
    FileWriter writer;
    public FileFitnessLogger(File file) throws IOException {
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
        BigInteger sumInstructX = BigInteger.ZERO;
        BigInteger instructionMultiplier = BigInteger.ONE;
        BigInteger nrInstructionMult = BigInteger.valueOf(nrInstruction);
        for (InstructionMark instruction : instructions)
        {
            int instructNr;
            if (instruction instanceof AccRegisterInstruction)
            {
                List opcodes = List.of(AccInstructionOpcodeEnum.values());
                if (instruction.getInstructionOpcode() instanceof AccInstructionOpcode)
                {
                    instructNr = opcodes.indexOf(((AccInstructionOpcode) instruction.getInstructionOpcode()).getEnumer()) + 1;
                } else if (instruction.getInstructionOpcode() instanceof RegularInstructionOpcode)
                {
                    instructNr = opcodes.indexOf(((RegularInstructionOpcode) instruction.getInstructionOpcode()).getEnumer()) + 1;
                }
                else {
                    throw new RuntimeException("Unknown class " + instruction.getClass().toString());
                }
                sumInstructX = sumInstructX.add(BigInteger.valueOf(instructNr).multiply(instructionMultiplier));
                instructionMultiplier = instructionMultiplier.multiply(nrInstructionMult);
            } else {
            switch(instruction.getClass().getSimpleName())
            {
                case "Add" : instructNr = 1; break;
                case "Cos" : instructNr = 2; break;
                case "Div" : instructNr = 3; break;
                case "Invert" : instructNr = 4; break;
                case "Log" : instructNr = 5; break;
                case "Mod" : instructNr = 6; break;
                case "Move" : instructNr = 7; break;
                case "Mul" : instructNr = 8; break;
                case "Nand" : instructNr = 9; break;
                case "Sin" : instructNr = 10; break;
                case "Sqrt" : instructNr = 11; break;
                case "Sub" : instructNr = 12; break;
                default: throw new RuntimeException("Unknown class " + instruction.getClass().toString());
            }
            }

            sumInstructX = sumInstructX.add(BigInteger.valueOf(instructNr).multiply(instructionMultiplier));
            instructionMultiplier = instructionMultiplier.multiply(nrInstructionMult);
        }

        BigInteger sumRegister = BigInteger.ZERO;
        BigInteger registerMultiplier = BigInteger.ONE;
        BigInteger nrRegisterMult = BigInteger.valueOf(nrRegisters);
        for (InstructionMark instruction : instructions)
        {
            String source = "";
            String dest = "";
            if (instruction instanceof DualRegisterInstruction)
            {
                source = ((DualRegisterInstruction) instruction).source.name;
                dest = ((DualRegisterInstruction) instruction).destination.name;
            } else if (instruction instanceof SingleRegisterInstruction){
                source = "R0";
                dest = ((SingleRegisterInstruction) instruction).destination.name;
            }else if (instruction instanceof AccRegisterInstruction)
            {
                dest = "R0";
                source = "R0";
            }
            int sourceNr = registerToInt(source);
            int destNr = registerToInt(dest);
            sumRegister = sumRegister.add(BigInteger.valueOf(sourceNr).multiply(registerMultiplier));
            registerMultiplier = registerMultiplier.multiply(nrRegisterMult);
            sumRegister = sumRegister.add(BigInteger.valueOf(destNr).multiply(registerMultiplier));
            registerMultiplier = registerMultiplier.multiply(nrRegisterMult);
        }
        return new BigInteger[]{sumInstructX, sumRegister};
    }


    public int registerToInt(String source) {
        int sourceNr;
        switch(source.toUpperCase())
        {
            case "R0" : sourceNr = 1; break;
            case "R1" : sourceNr = 2; break;
            case "R2" : sourceNr = 3; break;
            case "R3" : sourceNr = 4; break;
            case "R4" : sourceNr = 5; break;
            default: throw new RuntimeException("Unknown register " + source);
        }
        return sourceNr;
    }
}
