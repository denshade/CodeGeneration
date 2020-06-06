package laboflieven.accinstructions;

import laboflieven.AccProgram;
import laboflieven.ProgramResolution;
import laboflieven.statements.Instruction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class AccProgramResolution  implements Comparable<AccProgramResolution> {
    public List<AccRegisterInstruction> instructions;
    public double weight;

    public AccProgramResolution(List<AccRegisterInstruction> instructions, double weight) {
        this.instructions = instructions;
        this.weight = weight;
    }

    public List<List<AccRegisterInstruction>> procreate(AccProgramResolution partner, int nrChildren)
    {
        List<AccRegisterInstruction> childinstructions = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < instructions.size(); i++)
        {
            AccRegisterInstruction e;
            if (r.nextBoolean())
            {
                e = instructions.get(i);
            } else {
                e = partner.instructions.get(i);
            }
            childinstructions.add(e);
        }
        List<List<AccRegisterInstruction>> results = new ArrayList<>();
        for (int i = 0; i < nrChildren; i++)
        {
            List<AccRegisterInstruction> item = new ArrayList<>(childinstructions);
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
    public String toString() {
        return "ProgramResolution{" +
                "instructions=" + instructions +
                ", weight=" + weight +
                '}';
    }

}
