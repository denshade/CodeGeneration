package laboflieven.humanresource;

import laboflieven.humanresource.model.HumanInstruction;
import laboflieven.humanresource.model.HumanRegister;
import laboflieven.humanresource.model.HumanResourceProgram;
import laboflieven.humanresource.model.InvalidProgramException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by Lieven on 14/06/2015.
 */
public class HumanProgramFitnessExaminer
{
    private List<HumanInOutput> conditions;
    private int maxExecutions;


    public HumanProgramFitnessExaminer(List<HumanInOutput> conditions, int maxExecutions)
    {
        this.conditions = conditions;
        this.maxExecutions = maxExecutions;
    }

    public boolean isFit(List<HumanInstruction> instructions, List<HumanRegister> registers)
    {
        resetRegisters(registers);
        HumanStatementRunner runner = new HumanStatementRunner();
        HumanResourceProgram program = new HumanResourceProgram(instructions, registers);
        for (int currentCondition = 0; currentCondition < conditions.size(); currentCondition++)
        {
            Queue<Integer> outQ = new ArrayBlockingQueue<>(1000);
            HumanInOutput humanInOutput = conditions.get(currentCondition);
            Queue<Integer> inQ = new ArrayBlockingQueue<>(humanInOutput.input.size());
            inQ.addAll(humanInOutput.input);
            try {
                runner.execute(program, 0, inQ, outQ);
            } catch (InvalidProgramException ipe)
            {
                return false;
            }

            Iterator<Integer> it = outQ.iterator();
            if (outQ.size() != humanInOutput.output.size())
            {
                return false;
            }
            int inputIndex = 0;
            while (it.hasNext())
            {
                if (humanInOutput.output.get(inputIndex++) != it.next())
                {
                    return false;
                }
            }
        }
        //Should also check that expected values are actually compared. eg. R3 doesn't exist => OK.(wrong)
        return true;
    }

    private void resetRegisters(List<HumanRegister> registers) {
        for (HumanRegister register : registers) {
            register.value = null;
        }
    }

    public boolean isValid(List<HumanInstruction> instructions, List<HumanRegister> registers) {
        resetRegisters(registers);
        HumanStatementRunner runner = new HumanStatementRunner(maxExecutions);
        HumanResourceProgram program = new HumanResourceProgram(instructions, registers);
        for (int currentCondition = 0; currentCondition < conditions.size(); currentCondition++) {
            Queue<Integer> outQ = new ArrayBlockingQueue<>(1000);
            HumanInOutput currentExample = conditions.get(currentCondition);
            Queue<Integer> inQ = new ArrayBlockingQueue<>(currentExample.input.size());
            inQ.addAll(currentExample.input);
            try {
                runner.execute(program, 0, inQ, outQ);
                if (outQ.size() > currentExample.output.size()) // The program has more output than expected.
                    return false;
                Iterator<Integer> it = outQ.iterator();
                int inputIndex = 0;
                while (it.hasNext()) {
                    if (currentExample.output.get(inputIndex++) != it.next()) {
                        return false;
                    }
                }
            } catch (InvalidProgramException ipe) {
                return false;
            }
        }
        return true;
    }

    public double calculateFitness(List<HumanInstruction> instructions, List<HumanRegister> registers)
    {
        resetRegisters(registers);
        HumanStatementRunner runner = new HumanStatementRunner(maxExecutions);
        HumanResourceProgram program = new HumanResourceProgram(instructions, registers);
        int penalty = 0;
        for (int currentCondition = 0; currentCondition < conditions.size(); currentCondition++) {
            Queue<Integer> outQ = new ArrayBlockingQueue<>(1000);
            HumanInOutput currentExample = conditions.get(currentCondition);
            Queue<Integer> inQ = new ArrayBlockingQueue<>(currentExample.input.size());
            inQ.addAll(currentExample.input);
            try {
                runner.execute(program, 0, inQ, outQ);
                if (outQ.size() > currentExample.output.size()) // The program has more output than expected.
                    return 10000000;
                Iterator<Integer> it = outQ.iterator();
                int inputIndex = 0;

                while (it.hasNext()) {
                    if (currentExample.output.get(inputIndex++) != it.next()) {
                        penalty += 100;
                    }
                }
                penalty += 100 * Math.abs(currentExample.output.size() - outQ.size());
            } catch (InvalidProgramException ipe) {
                return 10000000;
            }
        }
        return penalty;
    }


}
