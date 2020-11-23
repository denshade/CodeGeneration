package laboflieven.examiners;

import laboflieven.InOutParameters;
import laboflieven.InstructionMark;
import laboflieven.Program;
import laboflieven.common.Configuration;
import laboflieven.loggers.FitnessLogger;
import laboflieven.runners.AccStatementRunner;
import laboflieven.runners.RegularStatementRunner;
import laboflieven.runners.StatementRunner;
import laboflieven.statements.Register;
import laboflieven.statements.RegularInstructionOpcodeEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Lieven on 14/06/2015.
 */
public class AccumulatorProgramFitnessExaminer implements ProgramFitnessExaminerInterface {
    private final List<InOutParameters> conditions;
    private final double closeEnough = 0.00001;
    private final List<FitnessLogger> loggers = new ArrayList<>();
    StatementRunner runner;

    /**
     * @param conditions Conditions that define the input parameters & the expected outcome.
     */
    public AccumulatorProgramFitnessExaminer(List<InOutParameters> conditions, AccStatementRunner runner)
    {
        this.conditions = conditions;
        this.runner = runner;
    }

    @Override
    public void addListener(FitnessLogger logger)
    {
        loggers.add(logger);
    }

    @Override
    public boolean isFit(List<InstructionMark> instructions, List<Register> registers)
    {
        return calculateFitness(instructions, registers) < closeEnough;
    }

    private boolean registerValueIsTooFar(Register register, Map<String, Double> expectedOutput) {
        return expectedOutput.containsKey(register.name) && Math.abs(expectedOutput.get(register.name) - register.value) > closeEnough;
    }

    @Override
    public double calculateFitness(List<InstructionMark> instructions, List<Register> registers)
    {
        Program program = new Program(instructions, registers);
        double noFitAtAll = Configuration.getInstance().getMaxError(Double.POSITIVE_INFINITY);
        double err = 0.0;
        total:
        for(InOutParameters parameter : conditions)
        {
            runner.execute(program, parameter.input);
            Map<String, Double> expectedOutput = parameter.expectedOutput;
            expectedOutput.get(registers.get(0).name);

            int foundExpectedRegisters = 0;
            for (Register register : program.getRegisters())
            {
                if (Double.isNaN(register.value) || Double.isInfinite(register.value))
                {
                    err = noFitAtAll;
                    break total;
                } else
                {
                    //expect R0 to contain the solution.
                    expectedOutput.get(registers.get(0).name);
                    if (expectedOutput.containsKey(register.name))
                    {
                        foundExpectedRegisters++;
                        err += Math.abs(expectedOutput.get(register.name) - register.value);
                        if (err >= noFitAtAll){
                            return noFitAtAll;
                        }
                        if (foundExpectedRegisters == expectedOutput.size()) break;
                    }
                }
            }
            //Should also check that expected values are actually compared. eg. R3 doesn't exist => OK.(wrong)
        }
        for(FitnessLogger logger : loggers)
        {
            logger.addFitness(instructions, RegularInstructionOpcodeEnum.values().length, registers.size(), err);
        }
        return err;
    }


    @Override
    public double evaluateDifference(Program program)
    {
        StatementRunner runner = new RegularStatementRunner();
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
