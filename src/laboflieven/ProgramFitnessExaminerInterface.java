package laboflieven;

import laboflieven.loggers.FitnessLogger;
import laboflieven.statements.Register;

import java.util.List;

public interface ProgramFitnessExaminerInterface {
    int NO_FIT_AT_ALL = 100000;

    void addListener(FitnessLogger logger);

    boolean isFit(List<InstructionMark> instructions, List<Register> registers);

    double calculateFitness(List<InstructionMark> instructions, List<Register> registers);

    double evaluateDifference(Program program);
}
