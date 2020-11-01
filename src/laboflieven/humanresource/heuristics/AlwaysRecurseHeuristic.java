package laboflieven.humanresource.heuristics;

import laboflieven.humanresource.model.HumanInstruction;

import java.util.List;

public class AlwaysRecurseHeuristic implements HumanRecursionHeuristic {

    @Override
    public boolean shouldRecurse(List<HumanInstruction> instructions) {
        return true;
    }
}
