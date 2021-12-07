package laboflieven.examiners;

import laboflieven.TestcaseInOutParameters;
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
    private List<TestcaseInOutParameters> conditions;
    private final double closeEnough = 0.00001;
    private final List<FitnessLogger> loggers = new ArrayList<>();
    private final String expectedResultRegister;
    StatementRunner runner;
    public List<TestcaseInOutParameters> getTestcases() {
        return conditions;
    }
    public void setTestcases(List<TestcaseInOutParameters> parameters){
        this.conditions = parameters;
    }

    /**
     * @param conditions Conditions that define the input parameters & the expected outcome.
     */
    public AccumulatorProgramFitnessExaminer(List<TestcaseInOutParameters> conditions, AccStatementRunner runner)
    {
        this (conditions, runner, AccStatementRunner.LEFT_ACC_NAME);
    }

    /**
     * @param conditions Conditions that define the input parameters & the expected outcome.
     */
    public AccumulatorProgramFitnessExaminer(List<TestcaseInOutParameters> conditions, AccStatementRunner runner, String expectedResultRegister)
    {
        this.conditions = conditions;
        this.runner = runner;
        this.expectedResultRegister = expectedResultRegister;
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
        for(TestcaseInOutParameters parameter : conditions)
        {
            Map<String, Double> results = runner.execute(program, parameter.input);
            Map<String, Double> expectedOutput = parameter.expectedOutput;
            Double value = results.get(expectedResultRegister);

            if (Double.isNaN(value) || Double.isInfinite(value))
            {
                err = noFitAtAll;
                break;
            } else
            {
                err += Math.abs(expectedOutput.get(registers.get(0).name) - value);
            }
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
        for(TestcaseInOutParameters parameter : conditions)
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
