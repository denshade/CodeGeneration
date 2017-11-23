package laboflieven;

import laboflieven.statements.Instruction;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Lieven on 20-11-2017.
 */
public class ProgramResolution implements Comparable<ProgramResolution>
{
    public List<Instruction> instructions;
    public double weight;

    List<Instruction> procreate(ProgramResolution partner)
    {
        List<Instruction> childinstructions = new ArrayList<>();
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
