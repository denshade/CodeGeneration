package laboflieven.functional.examiners;

import laboflieven.Program;
import laboflieven.TestcaseInOutParameters;
import laboflieven.runners.StatementRunner;

import java.util.Map;

public class ProgramTestcaseFitness
{
    public static double calculateError(StatementRunner runner, Program program, TestcaseInOutParameters parameter, String expectedResultRegister, double noFitAtAll)
    {
        double err;
        Map<String, Double> results = runner.execute(program, parameter.input);
        Map<String, Double> expectedOutput = parameter.expectedOutput;
        Double value = results.get(expectedResultRegister);

        if (Double.isNaN(value) || Double.isInfinite(value)) {
            err = noFitAtAll;
        } else
        {
            err = Math.abs(expectedOutput.get(program.getRegisters().get(0).name) - value);
        }
        return err;
    }
}
