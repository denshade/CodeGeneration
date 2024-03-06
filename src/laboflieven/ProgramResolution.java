package laboflieven;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Lieven on 20-11-2017.
 */
public class ProgramResolution implements Comparable<ProgramResolution>
{
    public List<InstructionMark> instructions;
    public double weight;

    public ProgramResolution(List<InstructionMark> instructions, double weight) {
        this.instructions = instructions;
        this.weight = weight;
    }

    List<InstructionMark> procreate(ProgramResolution partner)
    {
        List<InstructionMark> childinstructions = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < instructions.size(); i++)
        {
            if (r.nextBoolean())
            {
                childinstructions.add(instructions.get(i));
            } else {
                childinstructions.add(partner.instructions.get(i));
            }
        }
        return childinstructions;
    }

    public List<List<InstructionMark>> procreate(ProgramResolution partner, int nrChildren)
    {
        List<InstructionMark> childinstructions = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < instructions.size(); i++)
        {
            if (r.nextBoolean())
            {
                childinstructions.add(instructions.get(i));
            } else {
                childinstructions.add(partner.instructions.get(i));
            }
        }
        List<List<InstructionMark>> results = new ArrayList<>();
        for (int i = 0; i < nrChildren; i++)
        {
            List<InstructionMark> item = new ArrayList<>(childinstructions);
            Collections.shuffle(item);
            results.add(item);
        }
        return results;
    }

    @Override
    public int compareTo(ProgramResolution o) {
        return weight > o.weight? 1: -1;
    }

    @Override
    public String toString() {
        return "ProgramResolution{" +
                "instructions=" + instructions +
                ", weight=" + weight +
                '}';
    }
}
