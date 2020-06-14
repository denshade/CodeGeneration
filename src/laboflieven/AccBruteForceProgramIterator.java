package laboflieven;

import laboflieven.accinstructions.*;
import laboflieven.statements.Register;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by lveeckha on 31/05/2015.
 */
public class AccBruteForceProgramIterator
{
    public int maximumInstructions;
    public long counter = 0;

    public List<List<AccRegisterInstruction>> positiveSolutions = new ArrayList<>();
    private AccProgramFitnessExaminer evaluator;
    private InstructionEnum[] instructionEnums;
    public boolean stopAtFirstSolution = true;
    public boolean onlyEvaluateAtLastInstruction = true;


    public AccBruteForceProgramIterator(AccProgramFitnessExaminer evaluator)
    {
        this.evaluator = evaluator;
        instructionEnums = InstructionEnum.values();
    }

    public AccBruteForceProgramIterator(AccProgramFitnessExaminer evaluator, InstructionEnum[] instructions)
    {
        this.evaluator = evaluator;
        instructionEnums = instructions;
    }

    public List<List<AccRegisterInstruction>> iterate(final int nrOfRegisters, int maximumInstructions)
    {
        this.maximumInstructions = maximumInstructions;
        List<Register> registers =  Register.createRegisters(nrOfRegisters, "R");
        try {
            recurse(new ArrayList<>(), registers.toArray(new Register[0]));
        } catch (StopException ex)
        {
            //Allow quick termination.
        }
        return positiveSolutions;
    }

    private void recurse(List<AccRegisterInstruction> instructions, Register[] registers)
    {
        if (instructions.size() >= maximumInstructions)
            return;
        for (InstructionEnum instruction : instructionEnums)
        {
            //First instruction must be a push
            if (instructions.size() == 0 && !(instruction.equals(InstructionEnum.AccLeftPush) ||instruction.equals(InstructionEnum.AccRightPush)))
                continue;
            //Finish must be a push to a register.
            boolean isAccPushPull = !(instruction.equals(InstructionEnum.AccLeftPull) || instruction.equals(InstructionEnum.AccRightPull));
            if (instructions.size() == maximumInstructions - 1 && isAccPushPull)
                continue;
            //Don't use pull from right/left before a push.
            if (instruction.equals(InstructionEnum.AccLeftPull))
            {
                if (!hasAccLeftPush(instructions)) continue;
            }
            if (instruction.equals(InstructionEnum.AccRightPull))
            {
                if (!hasAccRightPush(instructions)) continue;
            }
            if (instructions.size() == maximumInstructions - 1 && isAccPushPull)
                continue;
            if (instruction.isSingleRegister()) {
                for (Register register1 : registers) {
                    AccRegisterInstruction actualInstruction = InstructionFactory.createInstruction(instruction, register1);
                    processInstruction(instructions, registers, actualInstruction);
                }
                } else {
                    AccRegisterInstruction actualInstruction = InstructionFactory.createInstruction(instruction);
                    processInstruction(instructions, registers, actualInstruction);
            }

        }
    }

    private void processInstruction(List<AccRegisterInstruction> instructions, Register[] registers, AccRegisterInstruction actualInstruction) {
        instructions.add(actualInstruction);
        eval(instructions, Arrays.asList(registers));
        recurse(instructions, registers);
        instructions.remove(instructions.size() - 1);
    }

    private boolean hasAccLeftPush(List<AccRegisterInstruction> instructions) {
        boolean used = false;
        for ( AccRegisterInstruction instructionI: instructions)
        {
            if (instructionI instanceof AccLeftPush) {
                used = true;
            }
        }
        return used;
    }

    private boolean hasAccRightPush(List<AccRegisterInstruction> instructions) {
        boolean used = false;
        for ( AccRegisterInstruction instructionI: instructions)
        {
            if (instructionI instanceof AccRightPush) {
                used = true;
            }
        }
        return used;
    }

    private void eval(List<AccRegisterInstruction> instructions, List<Register> registers) {
        if (onlyEvaluateAtLastInstruction && instructions.size() != maximumInstructions)
        {
            //Not yet!
        } else {
            if (evaluator.isFit(instructions, registers)){
                System.out.println("Found a program: " + instructions);
                positiveSolutions.add(new ArrayList<>(instructions));
                evaluator.calculateFitness(instructions, registers);
                if (stopAtFirstSolution) {
                    throw new StopException();
                }
            } else {
                double err = evaluator.calculateFitness(instructions, registers);
                if (err < 100 && counter++ % 100000 == 0)
                    System.out.println("Current fitness " + err + " " + instructions);
            }
        }
    }

    class StopException extends RuntimeException
    {

    }

}
