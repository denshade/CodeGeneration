package laboflieven.programiterators;

import laboflieven.common.RegularInstructionOpcode;
import laboflieven.examiners.ProgramFitnessExaminerInterface;
import laboflieven.InstructionMark;
import laboflieven.recursionheuristics.AlwaysRecursionHeuristic;
import laboflieven.recursionheuristics.RecursionHeuristic;
import laboflieven.statements.InstructionSet;
import laboflieven.statements.InstructionFactory;
import laboflieven.statements.Register;

import java.util.*;

/**
 * Created by lveeckha on 31/05/2015.
 */
public class BruteForceProgramIterator
{
    public int maximumInstructions;
    public long counter = 0;

    public List<List<InstructionMark>> positiveSolutions = new ArrayList<>();
    private ProgramFitnessExaminerInterface evaluator;
    private InstructionSet[] instructionSets;
    private RecursionHeuristic recursionHeuristic;
    private int nrRegisters;
    InstructionFactory instructionFactory = new InstructionFactory();


    public BruteForceProgramIterator(ProgramFitnessExaminerInterface evaluator)
    {
        this.evaluator = evaluator;
        instructionSets = InstructionSet.values();
        recursionHeuristic = new AlwaysRecursionHeuristic();
    }

    public BruteForceProgramIterator(ProgramFitnessExaminerInterface evaluator, RecursionHeuristic recursionHeuristic)
    {
        this.evaluator = evaluator;
        instructionSets = InstructionSet.values();
        this.recursionHeuristic = recursionHeuristic;
    }

    public BruteForceProgramIterator(ProgramFitnessExaminerInterface evaluator, RecursionHeuristic recursionHeuristic, InstructionSet[] instructions)
    {
        this.evaluator = evaluator;
        instructionSets = instructions;
        this.recursionHeuristic = recursionHeuristic;
    }

    public BruteForceProgramIterator(ProgramFitnessExaminerInterface evaluator, InstructionSet[] instructions)
    {
        this.evaluator = evaluator;
        instructionSets = instructions;
        recursionHeuristic = new AlwaysRecursionHeuristic();
    }

    public void iterate(final int nrOfRegisters, int maximumInstructions)
    {
        this.maximumInstructions = maximumInstructions;
        Register[] registers = Register.createRegisters(nrOfRegisters, "R").toArray(new Register[0]);
        this.nrRegisters = nrOfRegisters;
        recurse(new ArrayList<>(), registers);
    }

    private void recurse(List<InstructionMark> instructions, Register[] registers)
    {
        if (instructions.size() >= maximumInstructions)
            return;
        for (InstructionSet instruction : instructionSets)
        {
            for (Register register1 : registers) {
                if (instruction.isDualRegister()) {
                    for (Register register2 : registers) {
                        InstructionMark actualInstruction = instructionFactory.createInstruction(new RegularInstructionOpcode(instruction), register1, register2);
                        instructions.add(actualInstruction);
                        if (recursionHeuristic.shouldRecurse((List<InstructionMark>)(List<?>)instructions, nrRegisters))
                        {
                            eval(instructions, Arrays.asList(registers));
                            recurse(instructions, registers);
                        }

                        instructions.remove(instructions.size() - 1);
                    }
                } else {
                    InstructionMark actualInstruction = instructionFactory.createInstruction(new RegularInstructionOpcode(instruction), register1);
                    instructions.add(actualInstruction);
                    if (recursionHeuristic.shouldRecurse((List<InstructionMark>)(List<?>)instructions, nrRegisters)) {
                        eval(instructions, Arrays.asList(registers));
                        recurse(instructions, registers);
                    }
                    instructions.remove(instructions.size() - 1);
                }

            }
        }
    }

    private void eval(List<InstructionMark> instructions, List<Register> registers) {
        counter++;
        if (evaluator.isFit(instructions, registers)){
            System.out.println("Found a program: " + instructions);
            positiveSolutions.add(new ArrayList<>(instructions));
            evaluator.calculateFitness(instructions, registers);
        } else {
            evaluator.calculateFitness(instructions, registers);
            //System.out.println("Current fitness " + evaluator.calculateFitness(instructions, registers) + " " + instructions);
        }
    }

}
