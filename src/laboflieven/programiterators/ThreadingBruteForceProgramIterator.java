package laboflieven.programiterators;

import laboflieven.InstructionMark;
import laboflieven.common.AccInstructionOpcode;
import laboflieven.examiners.ProgramFitnessExaminerInterface;
import laboflieven.accinstructions.AccRegisterInstruction;
import laboflieven.accinstructions.InstructionEnum;
import laboflieven.accinstructions.InstructionFactory;
import laboflieven.recursionheuristics.AlwaysRecursionHeuristic;
import laboflieven.recursionheuristics.RecursionHeuristic;
import laboflieven.statements.InstructionFactoryInterface;
import laboflieven.statements.Register;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by lveeckha on 31/05/2015.
 */
public class ThreadingBruteForceProgramIterator
{
    public int maximumInstructions;
    public long counter = 0;

    public List<List<InstructionMark>> positiveSolutions = new ArrayList<>();
    private ProgramFitnessExaminerInterface evaluator;
    private InstructionEnum[] instructionEnums;
    private RecursionHeuristic heuristic = new AlwaysRecursionHeuristic();
    public boolean stopAtFirstSolution = true;
    public boolean onlyEvaluateAtLastInstruction = true;
    public InstructionFactoryInterface instructionFactory = new InstructionFactory();



    public ThreadingBruteForceProgramIterator(ProgramFitnessExaminerInterface evaluator)
    {
        this(evaluator, InstructionEnum.values());
    }

    public ThreadingBruteForceProgramIterator(ProgramFitnessExaminerInterface evaluator, InstructionEnum[] instructions)
    {
        this(evaluator, instructions, new AlwaysRecursionHeuristic());
    }
    public ThreadingBruteForceProgramIterator(ProgramFitnessExaminerInterface evaluator, InstructionEnum[] instructions, RecursionHeuristic heuristic)
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
        Arrays.stream(instructionEnums).parallel().forEach(instruction ->
        {
            if (heuristic.shouldRecurse(instructions, maximumInstructions)) {
                if (instruction.isSingleRegister()) {
                    for (Register register1 : registers) {
                        InstructionMark actualInstruction = instructionFactory.createInstruction(new AccInstructionOpcode(instruction), register1);
                        processInstruction(instructions, registers, (AccRegisterInstruction) actualInstruction);
                    }
                } else {
                    InstructionMark actualInstruction = instructionFactory.createInstruction(new AccInstructionOpcode(instruction));
                    processInstruction(instructions, registers, (AccRegisterInstruction) actualInstruction);
                }
            }
        });
    }

    private void processInstruction(List<InstructionMark> instructions, Register[] registers, AccRegisterInstruction actualInstruction) {
        instructions = new ArrayList<>(instructions);
        instructions.add(actualInstruction);
        eval(instructions, Arrays.asList(registers));
        recurse(instructions, registers);
//        instructions.remove(instructions.size() - 1);
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
