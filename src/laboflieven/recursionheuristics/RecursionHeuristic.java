package laboflieven.recursionheuristics;

import laboflieven.InstructionMark;
import laboflieven.statements.Instruction;

import java.util.List;

public interface RecursionHeuristic
{
    /**
     * @return Is it useful to recurse with this solution? For example Invert R1, Invert R1 is useless. So don't use this one.
     * @param instructions
     */
    boolean shouldRecurse(List<InstructionMark> instructions, int nrInstructions);
}
