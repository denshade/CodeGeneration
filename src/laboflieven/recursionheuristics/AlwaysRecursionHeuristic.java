package laboflieven.recursionheuristics;

import laboflieven.InstructionMark;
import laboflieven.statements.Instruction;

import java.util.List;

public class AlwaysRecursionHeuristic implements RecursionHeuristic
{

    @Override
    public boolean shouldRecurse(List<InstructionMark> instructions, int nrInstructions) {
        return true;
    }
}
