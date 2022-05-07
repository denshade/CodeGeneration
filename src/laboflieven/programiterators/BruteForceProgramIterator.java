package laboflieven.programiterators;

import laboflieven.Program;
import laboflieven.examiners.ProgramFitnessExaminerInterface;
import laboflieven.InstructionMark;
import laboflieven.recursionheuristics.AlwaysRecursionHeuristic;
import laboflieven.recursionheuristics.RecursionHeuristic;
import laboflieven.statements.RegularInstructionOpcodeEnum;
import laboflieven.statements.InstructionFactory;
import laboflieven.statements.Register;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by lveeckha on 31/05/2015.
 */
public class BruteForceProgramIterator
{
    public int maximumInstructions;
    public long counter = 0;

    public List<List<InstructionMark>> positiveSolutions = new ArrayList<>();
    private ProgramFitnessExaminerInterface evaluator;
    private RegularInstructionOpcodeEnum[] regularInstructionOpcodeEnums;
    private RecursionHeuristic recursionHeuristic;
    private int nrRegisters;
    InstructionFactory instructionFactory = new InstructionFactory();


    public BruteForceProgramIterator(ProgramFitnessExaminerInterface evaluator)
    {
        this.evaluator = evaluator;
        regularInstructionOpcodeEnums = RegularInstructionOpcodeEnum.values();
        recursionHeuristic = new AlwaysRecursionHeuristic();
    }

    public BruteForceProgramIterator(ProgramFitnessExaminerInterface evaluator, RecursionHeuristic recursionHeuristic)
    {
        this.evaluator = evaluator;
        regularInstructionOpcodeEnums = RegularInstructionOpcodeEnum.values();
        this.recursionHeuristic = recursionHeuristic;
    }

    public BruteForceProgramIterator(ProgramFitnessExaminerInterface evaluator, RecursionHeuristic recursionHeuristic, RegularInstructionOpcodeEnum[] instructions)
    {
        this.evaluator = evaluator;
        regularInstructionOpcodeEnums = instructions;
        this.recursionHeuristic = recursionHeuristic;
    }

    public BruteForceProgramIterator(ProgramFitnessExaminerInterface evaluator, RegularInstructionOpcodeEnum[] instructions)
    {
        this.evaluator = evaluator;
        regularInstructionOpcodeEnums = instructions;
        recursionHeuristic = new AlwaysRecursionHeuristic();
    }

    public void iterate(final int nrOfRegisters, int maximumInstructions)
    {
        this.maximumInstructions = maximumInstructions;
        Register[] registers = Register.createRegisters(nrOfRegisters).toArray(new Register[0]);
        this.nrRegisters = nrOfRegisters;
        recurse(new ArrayList<>(), registers);
    }

    private void recurse(List<InstructionMark> instructions, Register[] registers)
    {
        if (instructions.size() >= maximumInstructions)
            return;
        List<Register> registerList = Arrays.asList(registers);
        for (RegularInstructionOpcodeEnum instruction : regularInstructionOpcodeEnums)
        {
            for (Register register1 : registers) {
                if (instruction.isDualRegister()) {
                    for (Register register2 : registers) {
                        InstructionMark actualInstruction = instructionFactory.createInstruction(new laboflieven.common.RegularInstructionOpcode(instruction), register1, register2);
                        instructions.add(actualInstruction);
                        Program p = new Program(instructions, registerList);
                        if (recursionHeuristic.shouldRecurse(p, nrRegisters))
                        {
                            eval(instructions, registerList);
                            recurse(instructions, registers);
                        }

                        instructions.remove(instructions.size() - 1);
                    }
                } else {
                    InstructionMark actualInstruction = instructionFactory.createInstruction(new laboflieven.common.RegularInstructionOpcode(instruction), register1);
                    instructions.add(actualInstruction);
                    Program p = new Program(instructions, List.of(registers));
                    if (recursionHeuristic.shouldRecurse(p, nrRegisters)) {
                        eval(instructions, registerList);
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
