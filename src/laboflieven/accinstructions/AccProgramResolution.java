package laboflieven.accinstructions;


import laboflieven.InstructionMark;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class AccProgramResolution  implements ProgramResolutionI {
    public List<InstructionMark> instructions;
    public double weight;

    public AccProgramResolution(List<InstructionMark> instructions, double weight) {
        this.instructions = instructions;
        this.weight = weight;
    }

    @Override
    public List<List<InstructionMark>> procreate(AccProgramResolution partner, int nrChildren)
    {
        List<InstructionMark> childinstructions = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < instructions.size(); i++)
        {
            InstructionMark e;
            if (r.nextBoolean())
            {
                e = instructions.get(i);
            } else {
                e = partner.instructions.get(i);
            }
            childinstructions.add(e);
        }
        List<List<InstructionMark>> results = new ArrayList<>();
        for (int i = 0; i < nrChildren; i++)
        {
            List<InstructionMark> item = new ArrayList<InstructionMark>(childinstructions);
            Collections.shuffle(item);
            results.add(item);
        }
        return results;
    }

    @Override
    public int compareTo(AccProgramResolution o) {
        return weight > o.weight? 1: -1;
    }

    @Override
    public List<InstructionMark> getInstructions() {
        return instructions;
    }

    @Override
    public String toString() {
        return "ProgramResolution{" +
                "instructions=" + instructions +
                ", weight=" + weight +
                '}';
    }

}
