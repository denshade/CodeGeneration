package laboflieven.programiterators;

import laboflieven.InstructionMark;
import laboflieven.Program;
import laboflieven.ProgramResolution;
import laboflieven.accinstructions.AccInstructionOpcodeEnum;
import laboflieven.accinstructions.AccRegisterInstruction;
import laboflieven.accinstructions.InstructionFactory;
import laboflieven.common.Configuration;
import laboflieven.examiners.ProgramFitnessExaminerInterface;
import laboflieven.recursionheuristics.AlwaysRecursionHeuristic;
import laboflieven.recursionheuristics.RecursionHeuristic;
import laboflieven.statements.InstructionFactoryInterface;
import laboflieven.statements.Register;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by lveeckha on 31/05/2015.
 */
public class GeneralBruteForceProgramIterator implements ProgramIterator
{
    public int maximumInstructions;
    public long counter = 0;

    public List<List<InstructionMark>> positiveSolutions = new ArrayList<>();
    private ProgramFitnessExaminerInterface evaluator;
    private AccInstructionOpcodeEnum[] accInstructionOpcodeEnums = AccInstructionOpcodeEnum.values();
    private RecursionHeuristic heuristic = new AlwaysRecursionHeuristic();
    public boolean stopAtFirstSolution = false;
    public boolean onlyEvaluateAtLastInstruction = true;
    public InstructionFactoryInterface instructionFactory = new InstructionFactory();
    private List<Register> registers;
    private double errorTolerance = 0.0;


    public ProgramResolution iterate(Configuration configuration) {
        this.evaluator = configuration.getFitnessExaminer();
        this.accInstructionOpcodeEnums = configuration.getAccOperations();
        this.heuristic = configuration.getHeuristic(new AlwaysRecursionHeuristic());
        this.errorTolerance = configuration.getErrorTolerance(0.0);
        stopAtFirstSolution = configuration.getStopAtSolution(false);
        var instructions = runWithParams(configuration.getNumberOfRegisters(2), configuration.getMaxNrInstructions(10));
        if (instructions.size() > 0) {
            return new ProgramResolution(instructions.get(0), 0);
        }
        return null;
    }

    public List<List<InstructionMark>> iterate(final int nrOfRegisters, int maximumInstructions)
    {
        Configuration config = new Configuration();
        config.setMaxNrInstructions(maximumInstructions);
        config.setNumberOfRegisters(nrOfRegisters);
        iterate(config);
        return positiveSolutions;
    }

    private List<List<InstructionMark>> runWithParams(final int nrOfRegisters, int maximumInstructions)
    {
        this.maximumInstructions = maximumInstructions;
        registers =  Register.createRegisters(nrOfRegisters, "R");
        try {
            recurse(new ArrayList<>());
        } catch (StopException ex)
        {
            //Allow quick termination.
        }
        return positiveSolutions;
    }
//        var jumpIfZero = new Jump2IfLte();
//        var inc = new Inc();
//        var loadIntoRegister = new LoadAccLeftIntoRegister(r1);
//        var loadZeroIntoRegister = new LoadAccRightIntoRegister(r1);
//        var quit = new Quit();
    private void recurse(List<InstructionMark> instructions)
    {
        if (instructions.size() >= maximumInstructions)
            return;
        String h = "[LoadIntoLeftAcc, LoadAccRightIntoRegister, LoadAccLeftIntoVector, LoadVectorSumIntoLeft, Dec, Jump2IfLte, Quit, Inc";// Inc, LoadAccLeftIntoRegister
        if (instructions.stream().map(i -> i.getInstructionOpcode().toString()).collect(Collectors.toList()).toString().contains(h))
        {
            System.out.println("Hi");
        }
        for (AccInstructionOpcodeEnum instruction : accInstructionOpcodeEnums)
        {
            if (instruction.isSingleRegister()) {
                for (Register register1 : registers) {
                    InstructionMark actualInstruction = instructionFactory.createInstruction(new laboflieven.common.AccInstructionOpcode(instruction), register1);
                    processInstruction(instructions, (AccRegisterInstruction) actualInstruction);
                }
            } else {
                InstructionMark actualInstruction = instructionFactory.createInstruction(new laboflieven.common.AccInstructionOpcode(instruction));
                processInstruction(instructions, (AccRegisterInstruction) actualInstruction);
            }
        }
    }

    private void processInstruction(List<InstructionMark> instructions, AccRegisterInstruction actualInstruction) {
        instructions.add(actualInstruction);
        Program p = new Program(instructions, registers);
        if (heuristic.shouldRecurse(p, maximumInstructions)) {
            eval(instructions);
            recurse(instructions);
        }
        instructions.remove(instructions.size() - 1);
    }

    private void eval(List<InstructionMark> instructions) {
        if (onlyEvaluateAtLastInstruction && instructions.size() != maximumInstructions)
        {
            //Not yet!
        } else {
            counter++;
            double err = evaluator.calculateFitness(instructions, registers);
            if (err <= errorTolerance){
                System.out.println("Found a program: " + instructions);
                positiveSolutions.add(new ArrayList<>(instructions));
                if (stopAtFirstSolution) {
                    throw new StopException();
                }
            } else {
                if (err < 100 && counter % 100000 == 0)
                    System.out.println("Current fitness " + err + " " + instructions);
            }
            if (counter % 100000000 == 0) {
                System.out.println(instructions);
            }
        }
    }

    class StopException extends RuntimeException
    {

    }

}
