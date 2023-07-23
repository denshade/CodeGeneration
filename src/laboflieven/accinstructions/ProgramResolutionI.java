package laboflieven.accinstructions;

import laboflieven.InstructionMark;

import java.util.List;

public interface ProgramResolutionI extends Comparable<AccProgramResolution> {
    List<List<InstructionMark>> procreate(AccProgramResolution partner, int nrChildren);

    @Override
    int compareTo(AccProgramResolution o);
    List<InstructionMark> getInstructions();
}
