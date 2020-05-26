package laboflieven;

import laboflieven.accinstructions.AccProgramResolution;
import laboflieven.accinstructions.AccRegisterInstruction;
import laboflieven.accinstructions.InstructionEnum;
import laboflieven.accinstructions.InstructionFactory;
import laboflieven.common.BestFitRegister;
import laboflieven.common.PriorityQueueAlgos;
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
    private AccProgramFitnessExaminer evaluator;
    private InstructionEnum[] enums;
    private int maxPopulation;
    private Register[] registers;
    private int numberOfRegisters;
    private List<AccRegisterInstruction> bestSolution;
    private List<List<AccRegisterInstruction>> chosenSolutions;


    private double bestScore = 1000;
    public int initialPopSize = 1000;
    public int nrChildren = 3;

    public AccRandomGeneticProgramIterator(AccProgramFitnessExaminer evaluator, int maxPopulation, double maxOverflow, double popularParents) {
        this.evaluator = evaluator;
        POPULATION_MAX = maxPopulation;
        this.maxOverflow = maxOverflow;
        enums = InstructionEnum.values();
    }

    /**
     * @param evaluator
     * @param enums
     * @param maxPopulation  The maximum size of the population
     * @param maxOverflow    If the size of the population > maxPopulation * maxOverflow then we cut down the least popular solutions.
     * @param popularParents Only popular parents can breed. This is the percent of parents that are taken into account. e.g. 0.8
     */
    public AccRandomGeneticProgramIterator(AccProgramFitnessExaminer evaluator, InstructionEnum[] enums, int maxPopulation, double maxOverflow, double popularParents) {
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
        for (List<AccRegisterInstruction> instruction : chosenSolutions) {
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

        for (List<AccRegisterInstruction> childDNA : mom.procreate(dad, nrChildren)) {
            solutions.add(new AccProgramResolution(childDNA, eval(childDNA, Arrays.asList(registers))));
        }
    }

    private AccProgramResolution getNthVar(PriorityQueue<AccProgramResolution> solutions) {
        Random r = new Random();
        int p = r.nextInt((int) (solutions.size() * popularParents));
        return (AccProgramResolution) PriorityQueueAlgos.getNthBestSolution(solutions, p);
    }

    public void recurse(List<AccRegisterInstruction> instructions) {
        if (instructions.size() >= maximumInstructions) {
            chosenSolutions.add(new ArrayList<>(instructions));
            return;
        }

        Random r = new Random();
        AccProgram program = new AccProgram(instructions, Arrays.asList(registers));
        boolean foundProgram = false;
        while (!foundProgram) {
            InstructionEnum instruction;
            if (instructions.size() == 0) {
                instruction = pickRandomPush(r);
            } else {
                instruction = pickRandomInstruction(r);
            }

            AccRegisterInstruction actualInstruction;
            if (instruction.isSingleRegister()) {
                Register register1 = registers[r.nextInt(registers.length)];
                actualInstruction = InstructionFactory.createInstruction(instruction, register1);
            } else {
                actualInstruction = InstructionFactory.createInstruction(instruction);
            }
            if (!program.isUseless(actualInstruction, maximumInstructions)) {
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

    private double eval(List<AccRegisterInstruction> instructions, List<Register> registers) {
        return evaluator.calculateFitness(instructions, registers);
    }

}
