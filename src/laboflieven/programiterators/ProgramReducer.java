package laboflieven.programiterators;

import laboflieven.InstructionMark;
import laboflieven.examiners.ProgramFitnessExaminerInterface;
import laboflieven.registers.Register;

import java.util.ArrayList;
import java.util.List;

public class ProgramReducer {
    private final ProgramFitnessExaminerInterface examiner;

    public ProgramReducer(ProgramFitnessExaminerInterface examiner)
    {
        this.examiner = examiner;
    }

    public List<InstructionMark> reduce(List<InstructionMark> program, List<Register> registers)
    {
        if (!examiner.isFit(program, registers)) {
            throw new RuntimeException("Program is not fit.");
        }
        for (int i = 0; i < program.size(); i++)
        {
            var copyOfList = new ArrayList<>(program);
            copyOfList.remove(i);
            if (examiner.isFit(copyOfList, registers)) {
                return copyOfList;
            }
        }
        return program;
    }
    public List<InstructionMark> reduceAsFarAsPossible(List<InstructionMark> program, List<Register> registers)
    {
        var currentList = program;
        boolean wasReduced = true;
        while (wasReduced) {
            var reducedList = reduce(currentList, registers);
            wasReduced = currentList.size() != reducedList.size();
            currentList = reducedList;
        }
        return currentList;
    }
}
