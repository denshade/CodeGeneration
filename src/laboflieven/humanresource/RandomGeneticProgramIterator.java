package laboflieven.humanresource;

import laboflieven.humanresource.instructions.Inbox;
import laboflieven.humanresource.model.HumanInstruction;
import laboflieven.humanresource.model.HumanInstructionEnum;
import laboflieven.humanresource.model.HumanInstructionFactory;
import laboflieven.humanresource.model.HumanRegister;
import laboflieven.statements.InstructionSet;
import laboflieven.statements.Register;

import java.util.*;

/**
 * Created by lveeckha on 31/05/2015.
 */
public class RandomGeneticProgramIterator {
    public int POPULATION_MAX = 100;
    private double popularParents;
    private double maxOverflow;
    public int maximumInstructions;
    public long counter = 0;

    public List<List<HumanInstruction>> positiveSolutions = new ArrayList<>();
    private HumanProgramFitnessExaminer evaluator;
    private HumanInstructionEnum[] enums;
    private int maxPopulation;
    private HumanRegister[] registers;
    private int numberOfRegisters;
    private List<HumanInstruction> bestSolution;
    private List<List<HumanInstruction>> chosenSolutions;


    private double bestScore = 1000;

    public RandomGeneticProgramIterator(HumanProgramFitnessExaminer evaluator, int maxPopulation, double maxOverflow, double popularParents) {
        this.evaluator = evaluator;
        POPULATION_MAX = maxPopulation;
        this.maxOverflow = maxOverflow;
        enums = HumanInstructionEnum.values();
    }

    public RandomGeneticProgramIterator(HumanProgramFitnessExaminer evaluator, HumanInstructionEnum[] enums, int maxPopulation, double maxOverflow, double popularParents) {
        this.evaluator = evaluator;
        this.enums = enums;
        this.maxPopulation = maxPopulation;
        this.maxOverflow = maxOverflow;
        this.popularParents = popularParents;
    }

    public HumanProgramResolution iterate(int numberOfRegisters, int maximumInstructions) {
        chosenSolutions = new ArrayList<>();
        this.numberOfRegisters = numberOfRegisters;
        this.maximumInstructions = maximumInstructions;
        registers = new HumanRegister[numberOfRegisters];
        for (int i = 0; i < registers.length; i++) {
            registers[i] = new HumanRegister("r" + i);
        }
        HumanProgramResolution bestProgram = new HumanProgramResolution();
        Set<HumanRegister> availableRegisters = new HashSet<>();
        availableRegisters.add(registers[registers.length - 1]);// Add the result register.
        for (int i = 0; i < 10; i++) {

            while (chosenSolutions.size() < 1000) {
                recurse(new ArrayList<>(Arrays.asList(new Inbox())));

            }

            // System.out.println(chosenSolutions);
            PriorityQueue<HumanProgramResolution> solutions = new PriorityQueue<>();
            for (List<HumanInstruction> instruction : chosenSolutions) {
                HumanProgramResolution res = new HumanProgramResolution();
                res.weight = eval(instruction, Arrays.asList(registers));
                res.instructions = instruction;
                solutions.add(res);
            }
            double bestSolution = 446489;
            int bestSolutionCycle = 10000;
            //Let the best 10 solutions procreate.

            //while (solutions.peek().weight > 0 && bestSolutionCycle > 0)
            {

                HumanProgramResolution peek = solutions.peek();
                double weight = peek.weight;
                if (weight < bestSolution) {
                    bestSolution = weight;
                    bestSolutionCycle = 10000;
                    bestProgram.instructions = peek.instructions;
                    bestProgram.weight = weight;
                } else if (Math.abs(weight - bestSolution) < 0.005) {
                    bestSolutionCycle--;
                }
                System.out.println("Best solution " + weight + " " + solutions.peek().instructions);
//                reproduce(solutions);

            }
            System.out.println("Best solution" + solutions.peek().weight + " " + solutions.peek().instructions);
            chosenSolutions.clear();
        }
        return bestProgram;
    }

    private void reproduce(PriorityQueue<HumanProgramResolution> solutions) {
        HumanProgramResolution mom = getNthVar(solutions);
        HumanProgramResolution dad = getNthVar(solutions);

        for (List<HumanInstruction> childDNA : mom.procreate(dad, 3)) {
            HumanProgramResolution child = new HumanProgramResolution();
            child.instructions = childDNA;
            child.weight = eval(child.instructions, Arrays.asList(registers));
            solutions.add(child);
        }
    }

    private PriorityQueue cutPopulation(PriorityQueue solutions) {
        List l = new ArrayList<>();

        for (int i = 0; i < POPULATION_MAX; i++) {
            l.add(solutions.poll());
        }
        solutions = new PriorityQueue<>(l);
        return solutions;
    }

    private HumanProgramResolution getNthVar(PriorityQueue<HumanProgramResolution> solutions) {
        Random r = new Random();
        int p = r.nextInt((int) (solutions.size()));
        Iterator<HumanProgramResolution> it = solutions.iterator();
        HumanProgramResolution val = null;
        for (int k = 0; k <= p; k++) {
            val = it.next();
        }
        return val;
    }

    public void recurse(List<HumanInstruction> instructions) {
        if (instructions.size() >= maximumInstructions) {
            chosenSolutions.add(new ArrayList<>(instructions));
            return;
        }
        int instructionsLeft = maximumInstructions - instructions.size();
        if (instructionsLeft < 0) {
            return;
        }

        Random r = new Random();
        int location = r.nextInt(enums.length);
        HumanInstructionEnum instruction = enums[location];
        if (instruction.isSingleRegister()) {
            HumanRegister register1 = registers[r.nextInt(registers.length)];
            HumanInstruction actualInstruction = HumanInstructionFactory.createInstruction(instruction, register1);
            instructions.add(actualInstruction);
            if (!isValid(instructions, Arrays.asList(registers)))
                return;
        } else if (instruction.isLoop()) {
            int jumpToInstruction = r.nextInt(maximumInstructions);
            while (jumpToInstruction == instructions.size() - 1) {
                jumpToInstruction = r.nextInt(maximumInstructions);
            }
            HumanInstruction actualInstruction = HumanInstructionFactory.createLoopInstruction(instruction, jumpToInstruction);
            instructions.add(actualInstruction);
            if (!isValid(instructions, Arrays.asList(registers)))
                return;
        } else {
            HumanInstruction actualInstruction = HumanInstructionFactory.createInstruction(instruction);
            instructions.add(actualInstruction);
            if (!isValid(instructions, Arrays.asList(registers)))
                return;
        }
        recurse(instructions);
        instructions.remove(instructions.size() - 1);
    }

    private boolean isUselessOp(InstructionSet instruction, Register register1, Register register2) {
        return instruction == InstructionSet.Move && register1.name.equals(register2.name);
    }

    private double eval(List<HumanInstruction> instructions, List<HumanRegister> registers) {
        return evaluator.calculateFitness(instructions, registers);
    }

    private boolean isValid(List<HumanInstruction> instructions, List<HumanRegister> registers) {
        return evaluator.isValid(instructions, registers);
    }

}
