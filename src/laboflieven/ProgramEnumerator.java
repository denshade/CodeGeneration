package laboflieven;

import laboflieven.accinstructions.AccRegisterInstruction;
import laboflieven.statements.*;

import java.util.List;

public class ProgramEnumerator
{
    private final List<InstructionMark> options;
    private long actualMarks;
    private int nrRegisters;

    public ProgramEnumerator(List<InstructionMark> options, int nrRegisters)
    {
        this.options = options;
        actualMarks = 0;
        this.nrRegisters = nrRegisters;
        countInstructions(options, nrRegisters);
    }

    private void countInstructions(List<InstructionMark> options, int nrRegisters) {
        for (InstructionMark mark : options)
        {
            if (mark instanceof SingleRegisterInstruction || mark instanceof laboflieven.accinstructions.SingleRegisterInstruction) {
                actualMarks += nrRegisters;
            }
            if (mark instanceof DualRegisterInstruction) {
                actualMarks += nrRegisters * nrRegisters;
            }
            if (mark instanceof AccRegisterInstruction && !(mark instanceof laboflieven.accinstructions.SingleRegisterInstruction)){
                actualMarks ++;
            }
        }
    }

    public long convert(final List<InstructionMark> instructions)
    {
        long sum = 0;
        long l = 1;
        for (int currentInstruct = 0; currentInstruct < instructions.size(); currentInstruct++)
        {
            InstructionMark currentInstruction = instructions.get(currentInstruct);

            sum += getIndexForInstruction(currentInstruction) * l;
            l *= actualMarks;
        }
        return sum;
    }

    private long getIndexForInstruction(InstructionMark currentInstruction) {
        int k = 0;
        for (int index = 0; index < options.size(); index++)
        {
            InstructionMark currentOption = options.get(index);
            boolean match = false;
            if (currentOption.getClass().toGenericString().equals(currentInstruction.getClass().toGenericString()))
            {
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
            } else if (currentOption instanceof SingleRegisterInstruction ) {
                if (match) {

                    int index1 = getRegisterIndex(((SingleRegisterInstruction) currentOption).destination.name);
                    k += index1;
                    return k;
                }
                k += nrRegisters;
            } else if (currentOption instanceof laboflieven.accinstructions.SingleRegisterInstruction){
                if (match) {
                    int index1 = getRegisterIndex(((laboflieven.accinstructions.SingleRegisterInstruction) currentOption).getRegister().name);
                    k += index1;
                    return k;
                }
                k+= nrRegisters;
            } else
            if (currentOption instanceof AccRegisterInstruction){
                k ++;
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
