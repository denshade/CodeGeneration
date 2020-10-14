package laboflieven.recursionheuristics;

import laboflieven.InOutParameters;
import laboflieven.InstructionMark;
import laboflieven.Program;
import laboflieven.accinstructions.*;
import laboflieven.runners.AccStatementRunner;
import laboflieven.runners.StatementRunner;
import laboflieven.statements.Register;

import java.util.*;

public class HashedResultsHeuristic implements RecursionHeuristic
{
    private final List<InOutParameters> challenges;
    private final StatementRunner runner;
    private Map<String, Integer> hashMinInstructionMap = new HashMap<>();

    public HashedResultsHeuristic(List<InOutParameters> challenges, StatementRunner runner)
    {
        this.challenges = challenges;
        this.runner = runner;
    }

    @Override
    public boolean shouldRecurse(Program program, int maximumInstructions) {
        StringBuilder hash = new StringBuilder();
        for (InOutParameters challenge: challenges)
        {
            Map<String, Double> values =  runner.execute(program, challenge.input);
            hash.append(getHash(values));
        }
        if (hashMinInstructionMap.containsKey(hash.toString()))
        {
            if (program.getInstructions().size() >= hashMinInstructionMap.get(hash.toString())) {
                //System.out.println(program.getInstructions() + " skipped");
                return false;
            }
        }
        hashMinInstructionMap.put(hash.toString(), program.getInstructions().size());
        return true;
    }

    private String getHash(Map<String, Double> values) {
        StringBuilder hash = new StringBuilder();
        List<Double> dValues = new ArrayList<>(values.values());
        //Collections.sort(dValues);
        for (Double d : dValues) {
            hash.append(d);
        }
        return hash.toString();
    }

}
