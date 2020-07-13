package laboflieven;

import laboflieven.accinstructions.AccRegisterInstruction;
import laboflieven.statements.*;

import java.math.BigInteger;
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
        actualNrOptionsWithRegs = countInstructions(options, nrRegisters);
    }

    private long countInstructions(List<InstructionMark> options, int nrRegisters) {
        var count = 0;
        for (InstructionMark mark : options)
        {
            if (mark instanceof SingleRegisterInstruction || mark instanceof laboflieven.accinstructions.SingleRegisterInstruction) {
                count += nrRegisters;
            }
            if (mark instanceof DualRegisterInstruction) {
                count += nrRegisters * nrRegisters;
            }
            if (mark instanceof AccRegisterInstruction && !(mark instanceof laboflieven.accinstructions.SingleRegisterInstruction)){
                count++;
            }
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

    private long getIndexForInstruction(InstructionMark currentInstruction) {
        int k = 0;
        for (InstructionMark currentOption : options) {
            boolean match = false;
            if (currentOption.getClass().toGenericString().equals(currentInstruction.getClass().toGenericString())) {
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
