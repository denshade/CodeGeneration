package laboflieven.humanresource.heuristics;

import laboflieven.humanresource.instructions.Jump;
import laboflieven.humanresource.model.HumanInstruction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class CountInstructionWithJumpsHeuristic implements HumanRecursionHeuristic
{
    private final Logger LOGGER = Logger.getLogger(CountInstructionWithJumpsHeuristic.class.toString());
    private final Map<Class<?>, Integer> count;
    private final List<String> forbiddenCombos;

    public static Map<Class<?>, Integer> createMapOf(List<HumanInstruction> instructions) {
        Map<Class<?>, Integer> m = new HashMap<>();
        for (HumanInstruction instruct : instructions) {
            m.putIfAbsent(instruct.getClass(), 0);
            m.put(instruct.getClass(), m.get(instruct.getClass()) + 1);
        }
        return m;
    }

    public CountInstructionWithJumpsHeuristic(Map<Class<?>, Integer> count, List<String> forbiddenCombos){
        this.count = count;
        this.forbiddenCombos = forbiddenCombos;
    }

    @Override
    public boolean shouldRecurse(List<HumanInstruction> instructions) {
        for (Map.Entry<Class<?>, Integer> entry : count.entrySet()) {
            var filtered = instructions.stream().filter(c -> c.getClass().equals(entry.getKey())).collect(Collectors.toList());
            if (filtered.size() > entry.getValue())
            {
                return false;
            }
        }
        int lastJump = -5;
        for (int currentPos = 0; currentPos < instructions.size(); currentPos++) {
            var i = instructions.get(currentPos);
            if (i instanceof Jump) {
                if (currentPos == lastJump + 1){
                    return false;
                }
                lastJump = currentPos;
            }
        }
        for (String option : forbiddenCombos) {
            if (instructions.toString().contains(option)) {
                //LOGGER.warning("Skipped:"+option + " " + instructions);
                return false;
            }
        }
        return true;
    }
}
