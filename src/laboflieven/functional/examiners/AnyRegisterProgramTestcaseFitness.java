package laboflieven.functional.examiners;

import laboflieven.Program;
import laboflieven.TestcaseInOutParameters;
import laboflieven.runners.StatementRunner;

import java.util.HashMap;
import java.util.Map;

public class AnyRegisterProgramTestcaseFitness
{
    private static final double WRONG_REGISTER_ERROR = 10;
    public static double calculateError(StatementRunner runner, Program program, TestcaseInOutParameters parameter, String expectedResultRegister, double noFitAtAll)
    {
        return calculateError(runner.execute(program, parameter.input), program.getRegisters().get(0).name, parameter, expectedResultRegister, noFitAtAll);
    }
    public static double calculateError(Map<String, Double> programOutput , String firstRegisterName, TestcaseInOutParameters parameter, String expectedResultRegister, double noFitAtAll)
    {
        double err = noFitAtAll;
        Map<String, Double> expectedOutput = parameter.expectedOutput;
        double expectedOutputValue = expectedOutput.get(firstRegisterName);
        for (String register : programOutput.keySet()) {
            Double programValueByRegister = programOutput.get(register);
            double errForRegister = Math.abs(expectedOutputValue - programValueByRegister);
            if (!register.equals(firstRegisterName)) {
                errForRegister += WRONG_REGISTER_ERROR;
            }
            if (errForRegister < err) {
                err = errForRegister;
            }
        }

        return err;
    }
}
