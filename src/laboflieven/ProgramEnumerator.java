package laboflieven;

import laboflieven.accinstructions.AccRegisterInstruction;
import laboflieven.common.InstructionOpcode;
import laboflieven.statements.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class ProgramEnumerator
{
    private final List<InstructionMark> options;
    private long actualNrOptionsWithRegs;
    private int nrRegisters;

    public ProgramEnumerator(List<InstructionMark> options, int nrRegisters)
    {
        this.options = options;
        this.nrRegisters = nrRegisters;
        actualNrOptionsWithRegs = countInstructionsForOneLevel(options, nrRegisters);
    }

    private long countInstructionsForOneLevel(List<InstructionMark> options, int nrRegisters) {
        var count = 0;
        for (InstructionMark mark : options)
        {
            int nrRegistersForOpcode = mark.getInstructionOpcode().getNrRegisters();
            count += Math.pow(nrRegisters, nrRegistersForOpcode);
        }
        return count;
    }

    public BigInteger convert(final List<InstructionMark> instructions)
    {
        BigInteger sum = BigInteger.ZERO;
        BigInteger multiplier = BigInteger.ONE;
        BigInteger actualNrBigInt = BigInteger.valueOf(actualNrOptionsWithRegs);
        for (InstructionMark currentInstruction : instructions) {
            sum = sum.add(BigInteger.valueOf(getIndexForInstruction(currentInstruction)).multiply(multiplier));
            multiplier = multiplier.multiply(actualNrBigInt);
        }
        return sum;
    }

    public List<InstructionMark> convertToInstructions(final BigInteger instructionNr, InstructionFactoryInterface factoryInterface)
    {
        var instructions = new ArrayList<InstructionMark>();
        BigInteger workingNumber = instructionNr;
        BigInteger actualNrBigInt = BigInteger.valueOf(actualNrOptionsWithRegs);
        while(!workingNumber.equals(BigInteger.ZERO))
        {
            instructions.add(getIndexForOptions(workingNumber.mod(actualNrBigInt).longValue(),factoryInterface));
            workingNumber = workingNumber.divide(actualNrBigInt);
        }
        return instructions;
    }

    private InstructionMark getIndexForOptions(long instructionCounter, InstructionFactoryInterface factoryInterface) {
        long k = instructionCounter;
        for (InstructionMark currentOption : options) {

            if (currentOption instanceof DualRegisterInstruction) {
                if (k >= nrRegisters * nrRegisters)
                {
                    k -= nrRegisters* nrRegisters;
                } else {
                    long index2 = k % nrRegisters;
                    long index1 = k / nrRegisters;
                    return factoryInterface.createInstruction(currentOption.getInstructionOpcode(),
                            new Register("R" + (index1+1)),
                            new Register("R" + (index2+1)));
                }

            } else if (currentOption instanceof SingleRegisterInstruction || currentOption instanceof laboflieven.accinstructions.SingleRegisterInstruction) {
                if (k >= nrRegisters)
                {
                    k -= nrRegisters;
                } else {
                    long index1 = k % nrRegisters;
                    return factoryInterface.createInstruction(currentOption.getInstructionOpcode(),
                            new Register("R" + (index1+1)));
                }
            } else if (currentOption instanceof AccRegisterInstruction) {
                k--;
                if (k == 0) {
                    return factoryInterface.createInstruction(currentOption.getInstructionOpcode());
                }
            }

        }
        throw new RuntimeException("Option not found.");
    }

    private long getIndexForInstruction(InstructionMark currentInstruction) {
        int k = 0;
        for (InstructionMark currentOption : options) {
            boolean match = false;
            if (currentOption.getInstructionOpcode().equals(currentInstruction.getInstructionOpcode())) {
                match = true;
            }

            if (currentOption instanceof DualRegisterInstruction) {
                if (match) {
                    int index1 = getRegisterIndex(((DualRegisterInstruction) currentInstruction).destination.name);
                    int index2 = getRegisterIndex(((DualRegisterInstruction) currentInstruction).source.name);
                    k += index1 + nrRegisters * index2;
                    return k;
                }
                k += nrRegisters * nrRegisters;
            } else if (currentOption instanceof SingleRegisterInstruction) {
                if (match) {

                    int index1 = getRegisterIndex(((SingleRegisterInstruction) currentInstruction).destination.name);
                    k += index1;
                    return k;
                }
                k += nrRegisters;
            } else if (currentOption instanceof laboflieven.accinstructions.SingleRegisterInstruction) {
                if (match) {
                    int index1 = getRegisterIndex(((laboflieven.accinstructions.SingleRegisterInstruction) currentInstruction).getRegister().name);
                    k += index1;
                    return k;
                }
                k += nrRegisters;
            } else if (currentOption instanceof AccRegisterInstruction) {
                k++;
                if (match)
                    return k;
            }

        }
        throw new RuntimeException("Option not found.");
    }

    private int getRegisterIndex(String name) {
        if (name.toLowerCase().equals("r1")) return 0;
        if (name.toLowerCase().equals("r2")) return 1;
        if (name.toLowerCase().equals("r3")) return 2;
        if (name.toLowerCase().equals("r4")) return 3;
        throw new RuntimeException("Unknown register " + name);
    }
}
