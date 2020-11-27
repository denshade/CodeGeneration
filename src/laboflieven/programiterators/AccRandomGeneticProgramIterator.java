package laboflieven.programiterators;

import laboflieven.Program;
import laboflieven.accinstructions.*;
import laboflieven.InstructionMark;
import laboflieven.common.BestFitRegister;
import laboflieven.common.PriorityQueueAlgos;
import laboflieven.examiners.ProgramFitnessExaminer;
import laboflieven.examiners.ProgramFitnessExaminerInterface;
import laboflieven.statements.InstructionFactoryInterface;
import laboflieven.statements.Register;

import java.util.*;
import java.util.stream.IntStream;

/**
 * Created by lveeckha on 31/05/2015.
 */
public class AccRandomGeneticProgramIterator {
    public static final int BEST_SOLUTION_CYCLE = 10000000;
    public int POPULATION_MAX = 1000;
    private double popularParents;
    private double maxOverflow;
    public int maximumInstructions;
    public long counter = 0;

    public List<List<AccRegisterInstruction>> positiveSolutions = new ArrayList<>();
    private ProgramFitnessExaminerInterface evaluator;
    private AccInstructionOpcodeEnum[] enums;
    private int maxPopulation;
    private Register[] registers;
    private int numberOfRegisters;
    private List<InstructionMark> bestSolution;
    private List<List<InstructionMark>> chosenSolutions;
    private InstructionFactoryInterface instructionFactory = new InstructionFactory();


    private double bestScore = 1000;
    public int initialPopSize = 1000;
    public int nrChildren = 3;

    public AccRandomGeneticProgramIterator(ProgramFitnessExaminer evaluator, int maxPopulation, double maxOverflow, double popularParents) {
        this.evaluator = evaluator;
        POPULATION_MAX = maxPopulation;
        this.maxOverflow = maxOverflow;
        enums = AccInstructionOpcodeEnum.values();
    }

    /**
     * @param evaluator
     * @param enums
     * @param maxPopulation  The maximum size of the population
     * @param maxOverflow    If the size of the population > maxPopulation * maxOverflow then we cut down the least popular solutions.
     * @param popularParents Only popular parents can breed. This is the percent of parents that are taken into account. e.g. 0.8
     */
    public AccRandomGeneticProgramIterator(ProgramFitnessExaminerInterface evaluator, AccInstructionOpcodeEnum[] enums, int maxPopulation, double maxOverflow, double popularParents) {
        this.evaluator = evaluator;
        this.enums = enums;
        this.maxPopulation = maxPopulation;
        this.maxOverflow = maxOverflow;
        this.popularParents = popularParents;
        if (popularParents < 0 || popularParents > 1) {
            throw new IllegalArgumentException("PopularParents should be in [0,1]");
        }
    }

    public AccProgramResolution iterate(int numberOfRegisters, int maximumInstructions) {
        chosenSolutions = new ArrayList<>();
        this.numberOfRegisters = numberOfRegisters;
        this.maximumInstructions = maximumInstructions;
        registers = Register.createRegisters(numberOfRegisters, "R").toArray(new Register[0]);
        IntStream.range(0,  initialPopSize).forEach(k -> recurse(new ArrayList<>()));
        // System.out.println(chosenSolutions);
        PriorityQueue<AccProgramResolution> solutions = new PriorityQueue<>();
        for (List<InstructionMark> instruction : chosenSolutions) {
            solutions.add(new AccProgramResolution(instruction, eval(instruction, Arrays.asList(registers))));
        }
        int bestSolutionCycle = BEST_SOLUTION_CYCLE;
        //Let the best 10 solutions procreate.
        double overflowLimit = maxPopulation * maxOverflow;
        BestFitRegister<AccProgramResolution> register = new BestFitRegister<>();
        while (solutions.peek() != null && solutions.peek().weight > 0.10 && bestSolutionCycle > 0) {
            AccProgramResolution peekSolution = solutions.peek();
            if (register.register(peekSolution.weight, peekSolution))
            {
                bestSolutionCycle = BEST_SOLUTION_CYCLE;
            } else {
                bestSolutionCycle--;
            }
            reproduce(solutions);
            if (solutions.size() > overflowLimit) {
                solutions = PriorityQueueAlgos.cutPopulation(maxPopulation, solutions);
            }
        }
        //System.out.println("BestSolutionCycle:" + bestSolutionCycle);
        //System.out.println("Best solution" + solutions.peek().weight + " " + solutions.peek().instructions);
        return solutions.peek();
    }

    private void reproduce(PriorityQueue<AccProgramResolution> solutions) {
        AccProgramResolution mom = getNthVar(solutions);
        AccProgramResolution dad = getNthVar(solutions);

        for (List<InstructionMark> childDNA : mom.procreate(dad, nrChildren)) {
            solutions.add(new AccProgramResolution(childDNA, eval(childDNA, Arrays.asList(registers))));
        }
    }

    private AccProgramResolution getNthVar(PriorityQueue<AccProgramResolution> solutions) {
        Random r = new Random();
        int p = r.nextInt((int) (solutions.size() * popularParents));
        return (AccProgramResolution) PriorityQueueAlgos.getNthBestSolution(solutions, p);
    }

    public void recurse(List<InstructionMark> instructions) {
        if (instructions.size() >= maximumInstructions) {
            chosenSolutions.add(new ArrayList<InstructionMark>(instructions));
            return;
        }

        Random r = new Random();
        Program program = new Program(instructions, Arrays.asList(registers));
        boolean foundProgram = false;
        while (!foundProgram) {
            AccInstructionOpcodeEnum instruction;
            if (instructions.size() == 0) {
                instruction = pickRandomPush(r);
            } else {
                instruction = pickRandomInstruction(r);
            }

            InstructionMark actualInstruction;
            if (instruction.isSingleRegister()) {
                Register register1 = registers[r.nextInt(registers.length)];
                actualInstruction = instructionFactory.createInstruction(new laboflieven.common.AccInstructionOpcode(instruction), register1);
            } else {
                actualInstruction = instructionFactory.createInstruction(new laboflieven.common.AccInstructionOpcode(instruction));
            }
            if (!isUseless(program,(AccRegisterInstruction) actualInstruction, maximumInstructions)) {
                foundProgram = true;
                instructions.add(0, actualInstruction);
                recurse(instructions);
                instructions.remove(0);
            }
        }
    }

    public boolean isUseless(Program program, AccRegisterInstruction instruction, int maximumInstructions)
    {
        List<InstructionMark> instructions = program.getInstructions();
        //First instruction must be a push
        if (instructions.size() == 0 && !(instruction instanceof LoadIntoLeftAcc ||instruction instanceof LoadIntoRightAcc))
            return true;
        //Finish must be a push to a register.
        if (instructions.size() == maximumInstructions - 1 && !(instruction instanceof LoadAccLeftIntoRegister ||instruction instanceof LoadAccRightIntoRegister))
            return true;
        //Don't use pull from right/left before a push.
        if (instruction instanceof LoadAccLeftIntoRegister)
        {
            boolean used = false;
            for ( InstructionMark instructionI: instructions)
            {
                if (instructionI instanceof LoadIntoLeftAcc) {
                    used = true;
                }
            }
            if (!used)return true;
        }
        if (instruction instanceof LoadAccRightIntoRegister)
        {
            boolean used = false;
            for ( InstructionMark instructionI: instructions)
            {
                if (instructionI instanceof LoadIntoRightAcc) {
                    used = true;
                }
            }
            if (!used)return true;
        }
        return false;
    }


    private AccInstructionOpcodeEnum pickRandomPush(Random r) {
        AccInstructionOpcodeEnum instruction;
        if (r.nextBoolean()) {
            instruction = AccInstructionOpcodeEnum.LoadIntoLeftAcc;
        } else {
            instruction = AccInstructionOpcodeEnum.LoadIntoRightAcc;
        }
        return instruction;
    }

    private AccInstructionOpcodeEnum pickRandomInstruction(Random r) {
        int location = r.nextInt(enums.length);
        return enums[location];
    }

    private double eval(List<InstructionMark> instructions, List<Register> registers) {
        return evaluator.calculateFitness(instructions, registers);
    }

}
