package laboflieven;

import laboflieven.accinstructions.*;
import laboflieven.recursionheuristics.AccHeuristic;
import laboflieven.recursionheuristics.AlwaysRecursionHeuristic;
import laboflieven.recursionheuristics.RecursionHeuristic;
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

    public List<List<InstructionMark>> positiveSolutions = new ArrayList<>();
    private AccProgramFitnessExaminer evaluator;
    private InstructionEnum[] instructionEnums;
    private RecursionHeuristic heuristic = new AlwaysRecursionHeuristic();
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
    public AccBruteForceProgramIterator(AccProgramFitnessExaminer evaluator, InstructionEnum[] instructions, RecursionHeuristic heuristic)
    {
        this.evaluator = evaluator;
        instructionEnums = instructions;
        this.heuristic = heuristic;
    }

    public List<List<InstructionMark>> iterate(final int nrOfRegisters, int maximumInstructions)
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

    private void recurse(List<InstructionMark> instructions, Register[] registers)
    {
        if (instructions.size() >= maximumInstructions)
            return;
        for (InstructionEnum instruction : instructionEnums)
        {
            if (heuristic.shouldRecurse((List<InstructionMark>)(List<?>)instructions, maximumInstructions)) {
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
    }

    private void processInstruction(List<InstructionMark> instructions, Register[] registers, AccRegisterInstruction actualInstruction) {
        instructions.add(actualInstruction);
        eval(instructions, Arrays.asList(registers));
        recurse(instructions, registers);
        instructions.remove(instructions.size() - 1);
    }

    private void eval(List<InstructionMark> instructions, List<Register> registers) {
        if (onlyEvaluateAtLastInstruction && instructions.size() != maximumInstructions)
        {
            //Not yet!
        } else {
            if (evaluator.isFit(instructions, registers)){
                System.out.println("Found a program: " + instructions);
                positiveSolutions.add(new ArrayList<InstructionMark>(instructions));
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
