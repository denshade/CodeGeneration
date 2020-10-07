package laboflieven.recursionheuristics;

import laboflieven.InstructionMark;
import laboflieven.Program;

import java.util.List;

public class CombinedHeuristic implements RecursionHeuristic
{
    private final List<RecursionHeuristic> heuristics;

    public CombinedHeuristic(List<RecursionHeuristic> heuristics)
    {

        this.heuristics = heuristics;
    }
    @Override
    public boolean shouldRecurse(Program program, int nrInstructions) {
        for (RecursionHeuristic heuristic : heuristics)
        {
            if (!heuristic.shouldRecurse(program, nrInstructions)) return false;
        }
        return true;
    }
}
