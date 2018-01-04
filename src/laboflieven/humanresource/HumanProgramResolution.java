package laboflieven.humanresource;

import laboflieven.humanresource.model.HumanInstruction;
import laboflieven.statements.Instruction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Lieven on 20-11-2017.
 */
public class HumanProgramResolution implements Comparable<HumanProgramResolution>
{
    public List<HumanInstruction> instructions;
    public double weight;

    List<HumanInstruction> procreate(HumanProgramResolution partner)
    {
        List<HumanInstruction> childinstructions = new ArrayList<>();
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

    List<List<HumanInstruction>> procreate(HumanProgramResolution partner, int nrChildren)
    {
        List<HumanInstruction> childinstructions = new ArrayList<>();
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
        List<List<HumanInstruction>> results = new ArrayList<>();
        for (int i = 0; i < nrChildren; i++)
        {
            List<HumanInstruction> item = new ArrayList<>(childinstructions);
            Collections.shuffle(item);
            results.add(item);
        }
        return results;
    }

    @Override
    public int compareTo(HumanProgramResolution o) {
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
