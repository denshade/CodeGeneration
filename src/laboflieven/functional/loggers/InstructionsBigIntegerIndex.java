package laboflieven.functional.loggers;

import laboflieven.InstructionMark;
import laboflieven.common.InstructionOpcode;

import java.math.BigInteger;
import java.util.List;

public class InstructionsBigIntegerIndex
{
    private BigInteger sumInstructX;
    public InstructionsBigIntegerIndex(List<InstructionMark> instructions, final List<InstructionOpcode> opcodes)
    {
        sumInstructX = BigInteger.ZERO;
        BigInteger instructionMultiplier = BigInteger.ONE;
        BigInteger nrInstructionMult;
        nrInstructionMult = BigInteger.valueOf(opcodes.size());
        for (InstructionMark instruction : instructions)
        {
            int instructNr = opcodes.indexOf(instruction.getInstructionOpcode().getEnumeration());
            if (instructNr == -1) {
                throw new RuntimeException("Unknown class " + instruction.getClass().toString());
            }
            sumInstructX = sumInstructX.add(BigInteger.valueOf(instructNr).multiply(instructionMultiplier));
            instructionMultiplier = instructionMultiplier.multiply(nrInstructionMult);
        }
    }
    public BigInteger getSumRegister(){
        return sumInstructX;
    }
}
