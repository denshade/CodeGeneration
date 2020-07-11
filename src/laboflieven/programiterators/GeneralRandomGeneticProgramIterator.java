package laboflieven.programiterators;

import laboflieven.InstructionMark;
import laboflieven.common.AccInstructionSet;
import laboflieven.examiners.ProgramFitnessExaminerInterface;
import laboflieven.ProgramResolution;
import laboflieven.accinstructions.InstructionEnum;
import laboflieven.accinstructions.InstructionFactory;
import laboflieven.common.BestFitRegister;
import laboflieven.common.PriorityQueueAlgos;
import laboflieven.recursionheuristics.AlwaysRecursionHeuristic;
import laboflieven.recursionheuristics.RecursionHeuristic;
import laboflieven.statements.InstructionFactoryInterface;
import laboflieven.statements.Register;

import java.util.*;
import java.util.stream.IntStream;

/**
 * Created by lveeckha on 31/05/2015.
 */
public class GeneralRandomGeneticProgramIterator {
    public static final int BEST_SOLUTION_CYCLE = 10000000;
    public int POPULATION_MAX = 1000;
    private double popularParents;
    private double maxOverflow;
    public int maximumInstructions;
    public long counter = 0;

    public List<List<InstructionMark>> positiveSolutions = new ArrayList<>();
    private ProgramFitnessExaminerInterface evaluator;
    private InstructionEnum[] enums;
    private RecursionHeuristic heuristic = new AlwaysRecursionHeuristic();
    private int maxPopulation;
    private Register[] registers;
    private int numberOfRegisters;
    private List<InstructionMark> bestSolution;
    private List<List<InstructionMark>> chosenSolutions;
    public InstructionFactoryInterface instructionFactory = new InstructionFactory();



    private double bestScore = 1000;
    public int initialPopSize = 1000;
    public int nrChildren = 3;

    public GeneralRandomGeneticProgramIterator(ProgramFitnessExaminerInterface evaluator, int maxPopulation, double maxOverflow, double popularParents) {
        this(evaluator, InstructionEnum.values(), maxPopulation, maxOverflow, popularParents ); }

    /**
     * @param evaluator
     * @param enums
     * @param maxPopulation  The maximum size of the population
     * @param maxOverflow    If the size of the population > maxPopulation * maxOverflow then we cut down the least popular solutions.
     * @param popularParents Only popular parents can breed. This is the percent of parents that are taken into account. e.g. 0.8
     */
    public GeneralRandomGeneticProgramIterator(ProgramFitnessExaminerInterface evaluator, InstructionEnum[] enums, int maxPopulation, double maxOverflow, double popularParents) {
        this(evaluator, enums, maxPopulation, maxOverflow, popularParents, new AlwaysRecursionHeuristic());
    }

    public GeneralRandomGeneticProgramIterator(ProgramFitnessExaminerInterface evaluator, InstructionEnum[] enums, int maxPopulation, double maxOverflow, double popularParents, RecursionHeuristic heuristic) {
        this.evaluator = evaluator;
        this.enums = enums;
        this.maxPopulation = maxPopulation;
        this.maxOverflow = maxOverflow;
        this.popularParents = popularParents;
        this.heuristic = heuristic;
        if (popularParents < 0 || popularParents > 1) {
            throw new IllegalArgumentException("PopularParents should be in [0,1]");
        }
    }


    public ProgramResolution iterate(int numberOfRegisters, int maximumInstructions) {
        chosenSolutions = new ArrayList<>();
        this.numberOfRegisters = numberOfRegisters;
        this.maximumInstructions = maximumInstructions;
        registers = Register.createRegisters(numberOfRegisters, "R").toArray(new Register[0]);
        IntStream.range(0,  initialPopSize).forEach(k -> recurse(new ArrayList<>()));
        // System.out.println(chosenSolutions);
        PriorityQueue<ProgramResolution> solutions = new PriorityQueue<>();
        for (List<InstructionMark> instruction : chosenSolutions) {
            solutions.add(new ProgramResolution(instruction, eval(instruction, Arrays.asList(registers))));
        }
        int bestSolutionCycle = BEST_SOLUTION_CYCLE;
        //Let the best 10 solutions procreate.
        double overflowLimit = maxPopulation * maxOverflow;
        BestFitRegister<ProgramResolution> register = new BestFitRegister<>();
        while (solutions.peek() != null && solutions.peek().weight > 0.10 && bestSolutionCycle > 0) {
            ProgramResolution peekSolution = solutions.peek();
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

    private void reproduce(PriorityQueue<ProgramResolution> solutions) {
        ProgramResolution mom = getNthVar(solutions);
        ProgramResolution dad = getNthVar(solutions);

        for (List<InstructionMark> childDNA : mom.procreate(dad, nrChildren)) {
            solutions.add(new ProgramResolution(childDNA, eval(childDNA, Arrays.asList(registers))));
        }
    }

    private ProgramResolution getNthVar(PriorityQueue<ProgramResolution> solutions) {
        Random r = new Random();
        int p = r.nextInt((int) (solutions.size() * popularParents));
        return (ProgramResolution) PriorityQueueAlgos.getNthBestSolution(solutions, p);
    }

    public void recurse(List<InstructionMark> instructions) {
        if (instructions.size() >= maximumInstructions) {
            chosenSolutions.add(new ArrayList<>(instructions));
            return;
        }

        Random r = new Random();
        boolean foundProgram = false;
        while (!foundProgram) {
            InstructionEnum instruction;
            if (instructions.size() == 0) {
                instruction = pickRandomPush(r);
            } else {
                instruction = pickRandomInstruction(r);
            }

            InstructionMark actualInstruction;
            if (instruction.isSingleRegister()) {
                Register register1 = registers[r.nextInt(registers.length)];
                actualInstruction = instructionFactory.createInstruction(new AccInstructionSet(instruction), register1);
            } else {
                actualInstruction = instructionFactory.createInstruction(new AccInstructionSet(instruction));
            }
            if (heuristic.shouldRecurse(instructions, maximumInstructions)) {
                foundProgram = true;
                instructions.add(0, actualInstruction);
                recurse(instructions);
                instructions.remove(0);
            }
        }
    }

    private InstructionEnum pickRandomPush(Random r) {
        InstructionEnum instruction;
        if (r.nextBoolean()) {
            instruction = InstructionEnum.AccLeftPush;
        } else {
            instruction = InstructionEnum.AccRightPush;
        }
        return instruction;
    }

    private InstructionEnum pickRandomInstruction(Random r) {
        int location = r.nextInt(enums.length);
        return enums[location];
    }

    private double eval(List<InstructionMark> instructions, List<Register> registers) {
        return evaluator.calculateFitness(instructions, registers);
    }

}
