package laboflieven;

import laboflieven.statements.Instruction;
import laboflieven.statements.InstructionEnum;
import laboflieven.statements.InstructionFactory;
import laboflieven.statements.Register;

import java.util.*;

/**
 * Created by lveeckha on 31/05/2015.
 */
public class RandomGeneticProgramIterator {
    public int POPULATION_MAX = 1000;
    private double popularParents;
    private double maxOverflow;
    public int maximumInstructions;
    public long counter = 0;

    public List<List<Instruction>> positiveSolutions = new ArrayList<>();
    private ProgramFitnessExaminer evaluator;
    private InstructionEnum[] enums;
    private int maxPopulation;
    private Register[] registers;
    private int numberOfRegisters;
    private List<Instruction> bestSolution;
    private List<List<Instruction>> chosenSolutions;


    private double bestScore = 1000;

    public RandomGeneticProgramIterator(ProgramFitnessExaminer evaluator, int maxPopulation, double maxOverflow, double popularParents) {
        this.evaluator = evaluator;
        POPULATION_MAX = maxPopulation;
        this.maxOverflow = maxOverflow;
        enums = InstructionEnum.values();
    }


    public static List<Instruction>  trySolutions(InOutParameterSource source, InstructionEnum[] enums, double maxPopulationOverflow, int startPopulation, int maxPopulation,
                                    double minPopularParents, double maxPopularParents, int minRegisters, int maxRegisters) {
        double winnerOfTheWorldWeight = Double.MAX_VALUE;
        List<Instruction> bestProgram = null;

        for (int maxSizePopulation = startPopulation; maxSizePopulation < maxPopulation; maxSizePopulation += 10000) {
            for (double curpopularParents = minPopularParents; curpopularParents < maxPopularParents; curpopularParents += 0.2) {
                for (int curMaxRegisters = minRegisters; curMaxRegisters < maxRegisters; curMaxRegisters++) {
                    List<InOutParameters> collection = source.getInOutParameters(curMaxRegisters);

                    ProgramFitnessExaminer evaluator = new ProgramFitnessExaminer(collection);

                    for (int curMaxInstructions = 10; curMaxInstructions < 15; curMaxInstructions++) {

                        RandomGeneticProgramIterator iterator = new RandomGeneticProgramIterator(evaluator, // InstructionEnum.values(), //);
                                enums,
                                maxSizePopulation,
                                maxPopulationOverflow,
                                curpopularParents);

                        double bestInRetries = Double.MAX_VALUE;
                        ProgramResolution result = null;
                        for (int retries = 0; retries < 50; retries++) {

                            result = iterator.iterate(curMaxRegisters, curMaxInstructions);

                            if (result.weight < bestInRetries) {
                                bestInRetries = result.weight;
                                bestProgram = result.instructions;
                            }

                        }

                        if (bestInRetries < winnerOfTheWorldWeight) {
                            winnerOfTheWorldWeight = bestInRetries;
                            String winner = "#registers " + curMaxRegisters + " #maxinstructions " + curMaxInstructions + " curpopularparents " + curpopularParents + " #maxpopulation " + maxSizePopulation + ' ' + bestProgram;
                            System.out.println(winner + " with " + winnerOfTheWorldWeight);
                        }
                    }

                }
            }
        }
        return bestProgram;
    }

    public RandomGeneticProgramIterator(ProgramFitnessExaminer evaluator, InstructionEnum[] enums, int maxPopulation, double maxOverflow, double popularParents) {
        this.evaluator = evaluator;
        this.enums = enums;
        this.maxPopulation = maxPopulation;
        this.maxOverflow = maxOverflow;
        this.popularParents = popularParents;
    }

    public ProgramResolution iterate(int numberOfRegisters, int maximumInstructions) {
        chosenSolutions = new ArrayList<>();
        this.numberOfRegisters = numberOfRegisters;
        this.maximumInstructions = maximumInstructions;
        registers = Register.createRegisters(numberOfRegisters, "R").toArray(new Register[0]);
        Set<Register> availableRegisters = new HashSet<>();
        availableRegisters.add(registers[registers.length - 1]);// Add the result register.
        for (int i = 0; i < 1000; i++) {
            recurse(new ArrayList<>());
        }
       // System.out.println(chosenSolutions);
        PriorityQueue<ProgramResolution> solutions = new PriorityQueue<>();
        for (List<Instruction> instruction : chosenSolutions)
        {
            ProgramResolution res = new ProgramResolution();
            res.weight = eval(instruction, Arrays.asList(registers));
            res.instructions = instruction;
            solutions.add(res);
        }
        double bestSolution = Double.MAX_VALUE;
        int bestSolutionCycle = 10000;
        //Let the best 10 solutions procreate.

        while (solutions.peek().weight > 0.10 && bestSolutionCycle > 0)
        {
            double weight = solutions.peek().weight;
            if (weight < bestSolution)
            {
                bestSolution = weight;
                bestSolutionCycle = 10000;

            } else if (Math.abs(weight - bestSolution) < 0.005)
            {
                bestSolutionCycle--;
            }
            //System.out.println("Best solution " + weight);
            reproduce(solutions);
            //System.out.println(child);
            if (solutions.size() > POPULATION_MAX * maxOverflow)
            {
                solutions = cutPopulation(solutions);
            }
        }
        System.out.println("Best solution " + solutions.peek().weight + " " + solutions.peek().instructions);
        return solutions.peek();
    }

    private void reproduce(PriorityQueue<ProgramResolution> solutions) {
        ProgramResolution mom = getNthVar(solutions);
        ProgramResolution dad = getNthVar(solutions);

        for (List<Instruction> childDNA : mom.procreate(dad, 3))
        {
            ProgramResolution child = new ProgramResolution();
            child.instructions = childDNA;
            child.weight = eval(child.instructions, Arrays.asList(registers));
            solutions.add(child);
        }
    }

    private PriorityQueue<ProgramResolution> cutPopulation(PriorityQueue<ProgramResolution> solutions) {
        List<ProgramResolution> l = new ArrayList<>();
        if (solutions.size() < POPULATION_MAX)
        {
            return solutions;
        }
        for (int i = 0; i < POPULATION_MAX; i++)
        {
            l.add(solutions.poll());
        }
        solutions = new PriorityQueue<>(l);
        return solutions;
    }

    private ProgramResolution getNthVar(PriorityQueue<ProgramResolution> solutions) {
        Random r = new Random();
        int p = r.nextInt((int)(solutions.size() * popularParents));
        Iterator<ProgramResolution> it = solutions.iterator();
        ProgramResolution val = null;
        for (int k = 0; k <= p; k++)
        {
            val = it.next();
        }
        return val;
    }

    public void recurse(List<Instruction> instructions) {
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
        if (instruction.isDualRegister()) {
            Register register1 = registers[r.nextInt(registers.length)];
            Register register2 = registers[r.nextInt(registers.length)];

            if (isUselessOp(instruction, register1, register2)) {
                return;
            }

            Instruction actualInstruction = InstructionFactory.createInstruction(instruction, register1, register2);
            instructions.add(actualInstruction);
            //eval(instructions, Arrays.asList(registers));
            recurse(instructions);
            instructions.remove(0);
        } else {

            Register register1 = registers[r.nextInt(registers.length)];
            Instruction actualInstruction = InstructionFactory.createInstruction(instruction, register1);
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
        return instruction == InstructionEnum.Move && register1.name.equals(register2.name);
    }

    private double eval(List<Instruction> instructions, List<Register> registers) {
        return evaluator.calculateFitness(instructions, registers);
    }

}
