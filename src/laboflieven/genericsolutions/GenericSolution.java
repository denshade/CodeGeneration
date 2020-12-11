package laboflieven.genericsolutions;

import laboflieven.InstructionMark;

import java.util.ArrayList;
import java.util.List;

public class GenericSolution {
    public List<InstructionMark> programSelector = new ArrayList<>();
    public List<List<InstructionMark>> programs = new ArrayList<>();

    public String toString()
    {
        return "selector: " + programSelector + " solutions:" + programs;
    }
}
