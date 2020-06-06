package laboflieven;

import laboflieven.accinstructions.AccRegisterInstruction;
import laboflieven.accinstructions.InstructionEnum;
import laboflieven.loggers.AccFitnessLogger;
import laboflieven.loggers.FitnessLogger;
import laboflieven.statements.Register;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Lieven on 14/06/2015.
 */
public class AccProgramFitnessExaminer
{
    public static final int NO_FIT_AT_ALL = 100000;
    private List<InOutParameters> conditions;
    private final double closeEnough = 0.00001;
    private List<AccFitnessLogger> loggers = new ArrayList<>();
    AccStatementRunner runner = new AccStatementRunner();

    /**
     * @param conditions Conditions that define the input parameters & the expected outcome.
     */
    public AccProgramFitnessExaminer(List<InOutParameters> conditions)
    {
        this.conditions = conditions;
    }

    public void addListener(AccFitnessLogger logger)
    {
        loggers.add(logger);
    }

    public boolean isFit(List<AccRegisterInstruction> instructions, List<Register> registers)
    {
        return calculateFitness(instructions, registers) < closeEnough;
    }

    private boolean registerValueIsTooFar(Register register, Map<String, Double> expectedOutput) {
        return expectedOutput.containsKey(register.name) && Math.abs(expectedOutput.get(register.name) - register.value) > closeEnough;
    }

    public double calculateFitness(List<AccRegisterInstruction> instructions, List<Register> registers)
    {
        AccProgram program = new AccProgram(instructions, registers);
        double err = 0.0;

        out:
        for(InOutParameters parameter : conditions)
        {
            runner.execute(program, parameter.input);
            for (String key : parameter.expectedOutput.keySet())
            {
                double expectedValue = parameter.expectedOutput.get(key);
                Register output = program.getRegisterByName(key);
                if (Double.isNaN(output.value) || Double.isInfinite(output.value))
                {
                    err =  NO_FIT_AT_ALL;
                    break out;
                }
                err += Math.abs(expectedValue - output.value);
                if (err >= NO_FIT_AT_ALL){
                    err =  NO_FIT_AT_ALL;
                    break out;
                }
            }
        }

        for(AccFitnessLogger logger : loggers)
        {
            logger.addFitness(instructions, InstructionEnum.values().length, registers.size(), err);
        }
        return err;
    }

    private boolean programHasNANorInfiniteRegisterVals(Register register) {
        return Double.isNaN(register.value) || Double.isInfinite(register.value);
    }


    public double evaluateDifference(Program program)
    {
        StatementRunner runner = new StatementRunner();
        double result = 0.0;
        for(InOutParameters parameter : conditions)
        {
            runner.execute(program, parameter.input);
            for (Register register : program.getRegisters())
            {
                if (registerValueIsTooFar(register, parameter.expectedOutput))
                {
                    result += Math.abs(parameter.expectedOutput.get(register.name) - register.value);
                }
            }
            //Should also check that expected values are actually compared. eg. R3 doesn't exist => OK.(wrong)
        }
        return result;
    }
}
