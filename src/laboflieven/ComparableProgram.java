package laboflieven;

import java.util.List;

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

    public ComparableProgram(Program program, ProgramEvaluator evaluator) {
        this.program = program;
        score = evaluator.evaluateDifference(program);
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
