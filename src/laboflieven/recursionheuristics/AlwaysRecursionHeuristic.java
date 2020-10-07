package laboflieven.recursionheuristics;

import laboflieven.InstructionMark;
import laboflieven.Program;
import laboflieven.statements.Instruction;

import java.util.List;

public class AlwaysRecursionHeuristic implements RecursionHeuristic
{

    @Override
    public boolean shouldRecurse(Program program, int nrInstructions) {
        return true;
    }
}
