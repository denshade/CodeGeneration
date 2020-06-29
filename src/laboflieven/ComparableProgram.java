package laboflieven;

import laboflieven.examiners.ProgramFitnessExaminerInterface;

/**
 * Created by Lieven on 12-6-2016.
 */
public class ComparableProgram implements Comparable<ComparableProgram>
{
    public Program getProgram() {
        return program;
    }

    private Program program;

    public double getScore() {
        return score;
    }

    private double score;

    public ComparableProgram(Program program, ProgramFitnessExaminerInterface evaluator) {
        this.program = program;
        score = ProgramDifferenceScoringStrategy.evaluate(evaluator, program);
    }



    @Override
    public int compareTo(ComparableProgram o) {
        return (int)(score - o.score);
    }

    @Override
    public String toString() {
        return "ComparableProgram{" +
                ", program=" + program + ',' + score +
                '}';
    }
}
