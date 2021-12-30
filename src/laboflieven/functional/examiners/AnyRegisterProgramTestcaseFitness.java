package laboflieven.functional.examiners;

import laboflieven.Program;
import laboflieven.TestcaseInOutParameters;
import laboflieven.runners.StatementRunner;

import java.util.HashMap;
import java.util.Map;

public class AnyRegisterProgramTestcaseFitness
{
    private static double WRONG_REGISTER_ERROR = 10;
    public static double calculateError(StatementRunner runner, Program program, TestcaseInOutParameters parameter, String expectedResultRegister, double noFitAtAll)
    {
        return calculateError(runner.execute(program, parameter.input), program.getRegisters().get(0).name, parameter, expectedResultRegister, noFitAtAll);
    }
    public static double calculateError(Map<String, Double> programOutput , String firstRegisterName, TestcaseInOutParameters parameter, String expectedResultRegister, double noFitAtAll)
    {
        double err = noFitAtAll;
        Map<String, Double> expectedOutput = parameter.expectedOutput;
        Double programOutputValue = programOutput.get(expectedResultRegister);
        double expectedOutputValue = expectedOutput.get(firstRegisterName);
/*
        if (Double.isNaN(programOutputValue) || Double.isInfinite(programOutputValue)) {
            err = noFitAtAll;
        }
        /*else
        {
            err = Math.abs(expectedOutputValue - programOutputValue);
        }*/

        for (String register : programOutput.keySet()) {
            Double other = programOutput.get(register);
            double errForRegister = Math.abs(expectedOutputValue - other);
            if (!register.equals(firstRegisterName)) {
                errForRegister += 10;
            }
            if (errForRegister < err) {
                err = errForRegister;
            }
        }

        return err;
    }
}
