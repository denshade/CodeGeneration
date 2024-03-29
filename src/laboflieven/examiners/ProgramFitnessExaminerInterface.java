package laboflieven.examiners;

import laboflieven.InstructionMark;
import laboflieven.Program;
import laboflieven.TestcaseInOutParameters;
import laboflieven.loggers.FitnessLogger;
import laboflieven.registers.Register;

import java.util.List;

public interface ProgramFitnessExaminerInterface {
    int NO_FIT_AT_ALL = 100000;

    List<TestcaseInOutParameters> getTestcases();
    void setTestcases(List<TestcaseInOutParameters> parameters);


    void addListener(FitnessLogger logger);

    boolean isFit(List<InstructionMark> instructions, List<Register> registers);

    double calculateFitness(List<InstructionMark> instructions, List<Register> registers);

    double evaluateDifference(Program program);
}
