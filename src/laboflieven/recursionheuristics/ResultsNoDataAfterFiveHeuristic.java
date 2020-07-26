package laboflieven.recursionheuristics;

import laboflieven.InstructionMark;
import laboflieven.accinstructions.*;
import laboflieven.examiners.ProgramFitnessExaminerInterface;
import laboflieven.runners.StatementRunner;
import laboflieven.statements.Add;
import laboflieven.statements.Register;

import java.util.List;

public class ResultsNoDataAfterFiveHeuristic implements RecursionHeuristic
{
    private final StatementRunner runner;
    private final ProgramFitnessExaminerInterface programFitnessExaminerInterface;
    private final List<Register> registers;
    private final double horrible;

    public ResultsNoDataAfterFiveHeuristic(StatementRunner runner, ProgramFitnessExaminerInterface programFitnessExaminerInterface, List<Register> registers)
    {
        this.runner = runner;
        this.programFitnessExaminerInterface = programFitnessExaminerInterface;
        this.registers = registers;
        horrible = programFitnessExaminerInterface.calculateFitness(List.of(), registers);
    }
    @Override
    public boolean shouldRecurse(List<InstructionMark> instructionsMarks, int maximumInstructions) {
        if (instructionsMarks.size() == 3) {
            if (programFitnessExaminerInterface.calculateFitness(List.of(), registers) > horrible)
                return false;
        }
        return true;
    }

}
