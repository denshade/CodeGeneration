package laboflieven.recursionheuristics;

import laboflieven.Program;

public class AlwaysRecursionHeuristic implements RecursionHeuristic
{

    @Override
    public boolean shouldRecurse(Program program, int nrInstructions) {
        return true;
    }
}
