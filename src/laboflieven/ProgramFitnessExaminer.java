package laboflieven;

import laboflieven.challenges.FitnessLogger;
import laboflieven.statements.Instruction;
import laboflieven.statements.InstructionEnum;
import laboflieven.statements.Register;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Lieven on 14/06/2015.
 */
public class ProgramFitnessExaminer
{
    public static final int NO_FIT_AT_ALL = 100000;
    private List<InOutParameters> conditions;
    private final double closeEnough = 0.00001;
    private List<FitnessLogger> loggers = new ArrayList<>();
    /**
     * @param conditions Conditions that define the input parameters & the expected outcome.
     */
    public ProgramFitnessExaminer(List<InOutParameters> conditions)
    {
        this.conditions = conditions;
    }

    public void addListener(FitnessLogger logger)
    {
        loggers.add(logger);
    }

    public boolean isFit(List<Instruction> instructions, List<Register> registers)
    {
        StatementRunner runner = new StatementRunner();
        Program program = new Program(instructions, registers);

        for(InOutParameters parameter : conditions)
        {
            runner.execute(program, parameter.input);
            for (Register register : program.getRegisters())
            {
                Map<String, Double> expectedOutput = parameter.expectedOutput;
                if ( Double.isNaN(register.value) || Double.isInfinite(register.value) || registerValueIsCloseEnough(register, expectedOutput))
                {
                    return false;
                }
            }
            //Should also check that expected values are actually compared. eg. R3 doesn't exist => OK.(wrong)
        }
        return true;
    }

    private boolean registerValueIsCloseEnough(Register register, Map<String, Double> expectedOutput) {
        return expectedOutput.containsKey(register.name) && Math.abs(expectedOutput.get(register.name) - register.value) > closeEnough;
    }

    public double calculateFitness(List<Instruction> instructions, List<Register> registers)
    {
        StatementRunner runner = new StatementRunner();
        Program program = new Program(instructions, registers);
        double err = 0.0;
        total:
        for(InOutParameters parameter : conditions)
        {
            runner.execute(program, parameter.input);
            for (Register register : program.getRegisters())
            {
                Map<String, Double> expectedOutput = parameter.expectedOutput;
                if (Double.isNaN(register.value) || Double.isInfinite(register.value))
                {
                    err = NO_FIT_AT_ALL;
                    break total;
                } else
                {
                    if (expectedOutput.containsKey(register.name))
                        err += Math.min(NO_FIT_AT_ALL, Math.abs(expectedOutput.get(register.name) - register.value));
                }
            }
            //Should also check that expected values are actually compared. eg. R3 doesn't exist => OK.(wrong)
        }
        for(FitnessLogger logger : loggers)
        {
            logger.addFitness(instructions, InstructionEnum.values().length, registers.size(), err);
        }
        return err;
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
                if (registerValueIsCloseEnough(register, parameter.expectedOutput))
                {
                    result += Math.abs(parameter.expectedOutput.get(register.name) - register.value);
                }
            }
            //Should also check that expected values are actually compared. eg. R3 doesn't exist => OK.(wrong)
        }
        return result;
    }
}
