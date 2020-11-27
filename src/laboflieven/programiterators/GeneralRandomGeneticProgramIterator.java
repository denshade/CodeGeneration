package laboflieven.programiterators;

import laboflieven.InstructionMark;
import laboflieven.Program;
import laboflieven.common.Configuration;
import laboflieven.examiners.ProgramFitnessExaminerInterface;
import laboflieven.ProgramResolution;
import laboflieven.accinstructions.AccInstructionOpcodeEnum;
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
public class GeneralRandomGeneticProgramIterator implements ProgramIterator{
    public static final int BEST_SOLUTION_CYCLE = 10000000;
    public int POPULATION_MAX = 1000;
    private double popularParents;
    private double maxOverflow;
    public int maximumInstructions;
    public long counter = 0;

    public List<List<InstructionMark>> positiveSolutions = new ArrayList<>();
    private ProgramFitnessExaminerInterface evaluator;
    private AccInstructionOpcodeEnum[] enums;
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
        this(evaluator, AccInstructionOpcodeEnum.values(), maxPopulation, maxOverflow, popularParents ); }

    /**
     * @param evaluator
     * @param enums
     * @param maxPopulation  The maximum size of the population
     * @param maxOverflow    If the size of the population > maxPopulation * maxOverflow then we cut down the least popular solutions.
     * @param popularParents Only popular parents can breed. This is the percent of parents that are taken into account. e.g. 0.8
     */
    public GeneralRandomGeneticProgramIterator(ProgramFitnessExaminerInterface evaluator, AccInstructionOpcodeEnum[] enums, int maxPopulation, double maxOverflow, double popularParents) {
        this(evaluator, enums, maxPopulation, maxOverflow, popularParents, new AlwaysRecursionHeuristic());
    }
    public GeneralRandomGeneticProgramIterator() {
    }

    public GeneralRandomGeneticProgramIterator(ProgramFitnessExaminerInterface evaluator, AccInstructionOpcodeEnum[] enums, int maxPopulation, double maxOverflow, double popularParents, RecursionHeuristic heuristic) {
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

    @Override
    public ProgramResolution iterate(Configuration configuration) {
        chosenSolutions = new ArrayList<>();
        this.numberOfRegisters = configuration.getNumberOfRegisters(2);
        this.maximumInstructions = configuration.getMaxNrInstructions(10);
        this.evaluator = configuration.getFitnessExaminer();
        this.enums = configuration.getAccOperations();
        this.maxPopulation = configuration.getMaxPopulation();
        this.maxOverflow = configuration.getMaxOverFlow();
        this.popularParents = configuration.getPopularParents();
        this.heuristic = configuration.getHeuristic(new AlwaysRecursionHeuristic());
        if (popularParents < 0 || popularParents > 1) {
            throw new IllegalArgumentException("PopularParents should be in [0,1]");
        }

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
            Program p = new Program(instructions, List.of(registers));

            if (heuristic.shouldRecurse(p, maximumInstructions)) {
                foundProgram = true;
                instructions.add(0, actualInstruction);
                recurse(instructions);
                instructions.remove(0);
            }
        }
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
