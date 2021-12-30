package laboflieven.functional.examiners;

import laboflieven.Program;
import laboflieven.TestcaseInOutParameters;
import laboflieven.runners.StatementRunner;

import java.util.Map;

public class AnyRegisterProgramTestcaseFitness
{
    private static double WRONG_REGISTER_ERROR = 10;
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
    public static double calculateError(Map<String, Double> results , String firstRegisterName, TestcaseInOutParameters parameter, String expectedResultRegister, double noFitAtAll)
    {
        double err;
        Map<String, Double> expectedOutput = parameter.expectedOutput;
        Double value = results.get(expectedResultRegister);

        if (Double.isNaN(value) || Double.isInfinite(value)) {
            err = noFitAtAll;
        } else
        {
            err = Math.abs(expectedOutput.get(firstRegisterName) - value);
        }
        return err;
    }
}
