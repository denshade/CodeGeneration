package laboflieven.recursionheuristics;

import laboflieven.Program;

public interface RecursionHeuristic
{
    /**
     * @return Is it useful to recurse with this solution? For example Invert R1, Invert R1 is useless. So don't use this one.
     * @param instructions
     */
    boolean shouldRecurse(Program program, int nrInstructions);
}
