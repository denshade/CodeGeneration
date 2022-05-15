package laboflieven.functional.loggers;

import laboflieven.InstructionMark;
import laboflieven.accinstructions.AccRegisterInstruction;
import laboflieven.statements.DualRegisterInstruction;
import laboflieven.statements.SingleRegisterInstruction;

import java.math.BigInteger;
import java.util.List;

public class RegistersBigIntegerIndex
{
    private BigInteger sumRegister;
    public RegistersBigIntegerIndex(List<InstructionMark> instructions, int nrRegisters)
    {
        sumRegister = BigInteger.ZERO;
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
            }else if (instruction instanceof AccRegisterInstruction && instruction.getInstructionOpcode().getNrRegisters() == 0)
            {
                dest = "R0";
                source = "R0";
            }else if (instruction instanceof AccRegisterInstruction && instruction.getInstructionOpcode().getNrRegisters() == 1)
            {
                dest = ((laboflieven.accinstructions.SingleRegisterInstruction)instruction).getRegister().name;
                source = "R0";
            }
            int sourceNr = registerToInt(source);
            int destNr = registerToInt(dest);
            sumRegister = sumRegister.add(BigInteger.valueOf(sourceNr).multiply(registerMultiplier));
            registerMultiplier = registerMultiplier.multiply(nrRegisterMult);
            sumRegister = sumRegister.add(BigInteger.valueOf(destNr).multiply(registerMultiplier));
            registerMultiplier = registerMultiplier.multiply(nrRegisterMult);
        }
    }

    public BigInteger getSumRegister(){
        return sumRegister;
    }


    private int registerToInt(String source) {
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
