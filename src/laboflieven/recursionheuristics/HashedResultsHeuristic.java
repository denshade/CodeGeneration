package laboflieven.recursionheuristics;

import laboflieven.InstructionMark;
import laboflieven.Program;
import laboflieven.accinstructions.*;
import laboflieven.runners.AccStatementRunner;
import laboflieven.runners.StatementRunner;
import laboflieven.statements.Register;

import java.util.*;

public class HashedResultsHeuristic implements RecursionHeuristic
{
    private final Map<String, Double> challenge;
    private final StatementRunner runner;
    private Map<String, Integer> hashMinInstructionMap = new HashMap<>();

    public HashedResultsHeuristic(Map<String, Double> challenge, StatementRunner runner)
    {
        this.challenge = challenge;
        this.runner = runner;
    }

    @Override
    public boolean shouldRecurse(Program program, int maximumInstructions) {
        Map<String, Double> values =  runner.execute(program, challenge);
        String hash = getHash(values);
        if (hashMinInstructionMap.containsKey(hash))
        {
            if (program.getInstructions().size() >= hashMinInstructionMap.get(hash)) {
                return false;
            }
        }
        hashMinInstructionMap.put(hash, program.getInstructions().size());
        return true;
    }

    private String getHash(Map<String, Double> values) {
        StringBuilder hash = new StringBuilder();
        List<Double> dValues = new ArrayList<>(values.values());
        Collections.sort(dValues);
        for (Double d : dValues) {
            hash.append(d);
        }
        return hash.toString();
    }

}
