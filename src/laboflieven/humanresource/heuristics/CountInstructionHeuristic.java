package laboflieven.humanresource.heuristics;

import laboflieven.humanresource.model.HumanInstruction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CountInstructionHeuristic  implements HumanRecursionHeuristic
{
    public static Map<Class, Integer> createMapOf(List<HumanInstruction> instructions) {
        Map<Class, Integer> m = new HashMap<>();
        for (HumanInstruction instruct : instructions) {
            m.putIfAbsent(instruct.getClass(), 0);
            m.put(instruct.getClass(), m.get(instruct.getClass()) + 1);
        }
        return m;
    }
    private final Map<Class, Integer> count;

    public CountInstructionHeuristic(Map<Class, Integer> count){

        this.count = count;
    }

    @Override
    public boolean shouldRecurse(List<HumanInstruction> instructions) {
        for (Map.Entry<Class, Integer> entry : count.entrySet()) {
            var filtered = instructions.stream().filter(c -> c.getClass().equals(entry.getKey())).collect(Collectors.toList());
            if (filtered.size() > entry.getValue())
                return false;
        }
        return true;
    }
}
