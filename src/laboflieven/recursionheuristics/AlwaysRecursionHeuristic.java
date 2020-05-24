package laboflieven.recursionheuristics;

import laboflieven.statements.Instruction;

import java.util.List;

public class AlwaysRecursionHeuristic implements RecursionHeuristic
{

    @Override
    public boolean shouldRecurse(List<Instruction> instructions, int nrInstructions) {
        return true;
    }
}
