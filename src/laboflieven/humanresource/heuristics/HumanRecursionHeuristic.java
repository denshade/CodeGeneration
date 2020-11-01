package laboflieven.humanresource.heuristics;

import laboflieven.humanresource.model.HumanInstruction;

import java.util.List;

public interface HumanRecursionHeuristic {

    boolean shouldRecurse(List<HumanInstruction> instructions);

}
