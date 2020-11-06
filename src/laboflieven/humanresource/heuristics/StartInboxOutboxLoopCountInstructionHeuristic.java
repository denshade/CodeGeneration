package laboflieven.humanresource.heuristics;

import laboflieven.humanresource.instructions.CopyTo;
import laboflieven.humanresource.instructions.Inbox;
import laboflieven.humanresource.instructions.Jump;
import laboflieven.humanresource.instructions.Outbox;
import laboflieven.humanresource.model.HumanInstruction;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StartInboxOutboxLoopCountInstructionHeuristic implements HumanRecursionHeuristic
{

    private final Map<Class, Integer> count;
    private final int totalCount;

    public StartInboxOutboxLoopCountInstructionHeuristic(Map<Class, Integer> count, int totalCount){
        this.count = count;
        this.totalCount = totalCount;
    }

    @Override
    public boolean shouldRecurse(List<HumanInstruction> instructions) {
        if (instructions.size() > 0) {
            if (!(instructions.get(0) instanceof Inbox)) {
                return false;
            }
        }
        if (instructions.size() > 2) {
            var lastInstruct = instructions.get(instructions.size() - 1);
            var prevlastInstruct = instructions.get(instructions.size() - 2);

            if ((lastInstruct instanceof CopyTo) && (prevlastInstruct instanceof CopyTo) && lastInstruct.toString().equals(prevlastInstruct.toString())) {
                return false;
            }
        }
        for (int counter = 0; counter < instructions.size(); counter++) {
            if (instructions.get(counter) instanceof Outbox && counter != totalCount - 2) {
                return false;
            }
            if (instructions.get(counter) instanceof Jump && counter != totalCount - 1) {
                return false;
            }
        }
        for (Map.Entry<Class, Integer> entry : count.entrySet()) {
            var filtered = instructions.stream().filter(c -> c.getClass().equals(entry.getKey())).collect(Collectors.toList());
            if (filtered.size() > entry.getValue())
                return false;
        }
        return true;
    }
}
