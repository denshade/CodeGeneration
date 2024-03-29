package laboflieven.examiners;

import laboflieven.InstructionMark;
import laboflieven.Program;
import laboflieven.TestcaseInOutParameters;
import laboflieven.loggers.FitnessLogger;
import laboflieven.runners.AccStatementRunner;
import laboflieven.runners.RegularStatementRunner;
import laboflieven.runners.StatementRunner;
import laboflieven.registers.Register;
import laboflieven.instructions.regular.RegularInstructionOpcodeEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Lieven on 14/06/2015.
 */
public class ItemAccumulatorProgramFitnessExaminer implements ProgramFitnessExaminerInterface {
    private List<TestcaseInOutParameters> conditions;
    private final double closeEnough = 0.00001;
    private final List<FitnessLogger> loggers = new ArrayList<>();
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
    public ItemAccumulatorProgramFitnessExaminer(List<TestcaseInOutParameters> conditions, AccStatementRunner runner)
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
        double err = 0.0;
        for(TestcaseInOutParameters parameter : conditions)
        {
            Map<String, Double> results = runner.execute(program, parameter.input);
            Map<String, Double> expectedOutput = parameter.expectedOutput;
            Double value = results.get(AccStatementRunner.LEFT_ACC_NAME);

            if (Double.isNaN(value) || Double.isInfinite(value))
            {
                err = 1;
                break;
            } else
            { // error is x % of original value   x = y / e
                double expectedValue = expectedOutput.get(registers.get(0).name);
                double error = Math.abs(expectedValue - value);
                err += Math.abs(value) / error;
                //err += Math.abs(expectedOutput.get(registers.get(0).name) - value);
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
