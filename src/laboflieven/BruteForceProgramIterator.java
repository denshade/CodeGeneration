package laboflieven;

import laboflieven.recursionheuristics.AlwaysRecursionHeuristic;
import laboflieven.recursionheuristics.RecursionHeuristic;
import laboflieven.statements.Instruction;
import laboflieven.statements.InstructionEnum;
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

    public List<List<Instruction>> positiveSolutions = new ArrayList<>();
    private ProgramFitnessExaminer evaluator;
    private InstructionEnum[] instructionEnums;
    private RecursionHeuristic recursionHeuristic;
    private int nrRegisters;


    public BruteForceProgramIterator(ProgramFitnessExaminer evaluator)
    {
        this.evaluator = evaluator;
        instructionEnums = InstructionEnum.values();
        recursionHeuristic = new AlwaysRecursionHeuristic();
    }

    public BruteForceProgramIterator(ProgramFitnessExaminer evaluator, RecursionHeuristic recursionHeuristic)
    {
        this.evaluator = evaluator;
        instructionEnums = InstructionEnum.values();
        this.recursionHeuristic = recursionHeuristic;
    }

    public BruteForceProgramIterator(ProgramFitnessExaminer evaluator, InstructionEnum[] instructions)
    {
        this.evaluator = evaluator;
        instructionEnums = instructions;
        recursionHeuristic = new AlwaysRecursionHeuristic();
    }

    public void iterate(final int nrOfRegisters, int maximumInstructions)
    {
        this.maximumInstructions = maximumInstructions;
        Register[] registers = Register.createRegisters(nrOfRegisters, "R").toArray(new Register[0]);
        this.nrRegisters = nrOfRegisters;
        recurse(new ArrayList<>(), registers);
    }

    private void recurse(List<Instruction> instructions, Register[] registers)
    {
        if (instructions.size() >= maximumInstructions)
            return;
        for (InstructionEnum instruction : instructionEnums)
        {
            for (Register register1 : registers) {
                if (instruction.isDualRegister()) {
                    for (Register register2 : registers) {
                        Instruction actualInstruction = InstructionFactory.createInstruction(instruction, register1, register2);
                        instructions.add(actualInstruction);
                        if (recursionHeuristic.shouldRecurse(instructions, nrRegisters))
                        {
                            eval(instructions, Arrays.asList(registers));
                            recurse(instructions, registers);
                        }

                        instructions.remove(instructions.size() - 1);
                    }
                } else {
                    Instruction actualInstruction = InstructionFactory.createInstruction(instruction, register1);
                    instructions.add(actualInstruction);
                    if (recursionHeuristic.shouldRecurse(instructions, nrRegisters)) {
                        eval(instructions, Arrays.asList(registers));
                        recurse(instructions, registers);
                    }
                    instructions.remove(instructions.size() - 1);
                }

            }
        }
    }

    private void eval(List<Instruction> instructions, List<Register> registers) {
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
