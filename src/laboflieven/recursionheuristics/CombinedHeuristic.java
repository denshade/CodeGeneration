package laboflieven.recursionheuristics;

import laboflieven.InstructionMark;

import java.util.List;

public class CombinedHeuristic implements RecursionHeuristic
{
    private final List<RecursionHeuristic> heuristics;

    public CombinedHeuristic(List<RecursionHeuristic> heuristics)
    {

        this.heuristics = heuristics;
    }
    @Override
    public boolean shouldRecurse(List<InstructionMark> instructions, int nrInstructions) {
        for (RecursionHeuristic heuristic : heuristics)
        {
            if (!heuristic.shouldRecurse(instructions, nrInstructions)) return false;
        }
        return true;
    }
}
