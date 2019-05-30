package laboflieven;

import laboflieven.accinstructions.AccProgramResolution;
import laboflieven.accinstructions.AccRegisterInstruction;
import laboflieven.accinstructions.InstructionEnum;
import laboflieven.accinstructions.InstructionFactory;
import laboflieven.statements.Register;

import java.util.*;

/**
 * Created by lveeckha on 31/05/2015.
 */
public class AccRandomGeneticProgramIterator {
    public static final int BEST_SOLUTION_CYCLE = 100000;
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

    public AccRandomGeneticProgramIterator(AccProgramFitnessExaminer evaluator, int maxPopulation, double maxOverflow, double popularParents) {
        this.evaluator = evaluator;
        POPULATION_MAX = maxPopulation;
        this.maxOverflow = maxOverflow;
        enums = InstructionEnum.values();
    }

    public AccRandomGeneticProgramIterator(AccProgramFitnessExaminer evaluator, InstructionEnum[] enums, int maxPopulation, double maxOverflow, double popularParents) {
        this.evaluator = evaluator;
        this.enums = enums;
        this.maxPopulation = maxPopulation;
        this.maxOverflow = maxOverflow;
        this.popularParents = popularParents;
    }

    public AccProgramResolution iterate(int numberOfRegisters, int maximumInstructions) {
        chosenSolutions = new ArrayList<>();
        this.numberOfRegisters = numberOfRegisters;
        this.maximumInstructions = maximumInstructions;
        registers = new Register[numberOfRegisters];
        for (int i = 0; i < registers.length; i++) {
            registers[i] = new Register("r" + i);
        }
        Set<Register> availableRegisters = new HashSet<>();
        availableRegisters.add(registers[registers.length - 1]);// Add the result register.
        for (int i = 0; i < 1000; i++) {
            recurse(new ArrayList<>());
        }
       // System.out.println(chosenSolutions);
        PriorityQueue<AccProgramResolution> solutions = new PriorityQueue<>();
        for (List<AccRegisterInstruction> instruction : chosenSolutions)
        {
            AccProgramResolution res = new AccProgramResolution();
            res.weight = eval(instruction, Arrays.asList(registers));
            res.instructions = instruction;
            solutions.add(res);
        }
        double bestSolution = 446489;
        int bestSolutionCycle = BEST_SOLUTION_CYCLE;
        //Let the best 10 solutions procreate.

        while (solutions.peek().weight > 0.10 && bestSolutionCycle > 0)
        {
            double weight = solutions.peek().weight;
            if (weight < bestSolution)
            {
                bestSolution = weight;
                bestSolutionCycle = BEST_SOLUTION_CYCLE;

            } else if (Math.abs(weight - bestSolution) < 0.005)
            {
                bestSolutionCycle--;
            }
            System.out.println("Best solution " + weight);
            reproduce(solutions);
            //System.out.println(child);
            if (solutions.size() > POPULATION_MAX * maxOverflow)
            {
                solutions = cutPopulation(solutions);
            }
        }
        System.out.println("Best solution" + solutions.peek().weight + " " + solutions.peek().instructions);
        return solutions.peek();
    }

    private void reproduce(PriorityQueue<AccProgramResolution> solutions) {
        AccProgramResolution mom = getNthVar(solutions);
        AccProgramResolution dad = getNthVar(solutions);

        for (List<AccRegisterInstruction> childDNA : mom.procreate(dad, 3))
        {
            AccProgramResolution child = new AccProgramResolution();
            child.instructions = childDNA;
            child.weight = eval(child.instructions, Arrays.asList(registers));
            solutions.add(child);
        }
    }

    private PriorityQueue<AccProgramResolution> cutPopulation(PriorityQueue<AccProgramResolution> solutions) {
        List l = new ArrayList<>();

        for (int i = 0; i < POPULATION_MAX; i++)
        {
            l.add(solutions.poll());
        }
        solutions = new PriorityQueue<>(l);
        return solutions;
    }

    private AccProgramResolution getNthVar(PriorityQueue<AccProgramResolution> solutions) {
        Random r = new Random();
        int p = r.nextInt((int)(solutions.size() * popularParents));
        Iterator<AccProgramResolution> it = solutions.iterator();
        AccProgramResolution val = null;
        for (int k = 0; k <= p; k++)
        {
            val = it.next();
        }
        return val;
    }

    public void recurse(List<AccRegisterInstruction> instructions) {
        if (instructions.size() >= maximumInstructions)
        {
            chosenSolutions.add(new ArrayList<>(instructions));
            return;
        }
        int instructionsLeft = maximumInstructions - instructions.size();
        if (instructionsLeft < 0) {
            return;
        }

        Random r = new Random();
        int location = r.nextInt(enums.length);
        InstructionEnum instruction = enums[location];
        if (instruction.isSingleRegister()) {
            Register register1 = registers[r.nextInt(registers.length)];

            AccRegisterInstruction actualInstruction = InstructionFactory.createInstruction(instruction, register1);
            instructions.add(actualInstruction);
            //eval(instructions, Arrays.asList(registers));
            recurse(instructions);
            instructions.remove(0);
        } else {

            AccRegisterInstruction actualInstruction = InstructionFactory.createInstruction(instruction);
            instructions.add(0, actualInstruction);
            //eval(instructions, Arrays.asList(registers));
            /**
             * Available registers remains the same. No new registers are used.
             */
            recurse(instructions);
            instructions.remove(0);

        }
    }

    private boolean isUselessOp(InstructionEnum instruction, Register register1, Register register2) {
        return false;
    }

    private double eval(List<AccRegisterInstruction> instructions, List<Register> registers) {
        return evaluator.calculateFitness(instructions, registers);
    }

}
