package laboflieven.programiterators;

import laboflieven.*;
import laboflieven.examiners.ProgramFitnessExaminer;
import laboflieven.examiners.ProgramFitnessExaminerInterface;
import laboflieven.runners.RegularStatementRunner;
import laboflieven.statements.*;

import java.util.*;

/**
 * Created by lveeckha on 31/05/2015.
 */
public class RandomGeneticProgramIterator {
    public int POPULATION_MAX = 1000;
    private double popularParents;
    private final double maxOverflow;
    public int maximumInstructions;
    public long counter = 0;

    public List<List<InstructionMark>> positiveSolutions = new ArrayList<>();
    private final ProgramFitnessExaminerInterface evaluator;
    private final RegularInstructionOpcodeEnum[] enums;
    private int maxPopulation;
    private Register[] registers;
    private int numberOfRegisters;
    private List<InstructionMark> bestSolution;
    protected List<List<InstructionMark>> chosenSolutions;


    private final double bestScore = 1000;
    private final InstructionFactoryInterface instructionFactory = new InstructionFactory();

    public RandomGeneticProgramIterator(ProgramFitnessExaminerInterface evaluator, int maxPopulation, double maxOverflow, double popularParents) {
        this.evaluator = evaluator;
        POPULATION_MAX = maxPopulation;
        this.maxOverflow = maxOverflow;
        enums = RegularInstructionOpcodeEnum.values();
        chosenSolutions = new ArrayList<>();
    }


    public static List<InstructionMark>  trySolutions(InOutParameterSource source, RegularInstructionOpcodeEnum[] enums, double maxPopulationOverflow, int startPopulation, int maxPopulation,
                                                      double minPopularParents, double maxPopularParents, int minRegisters, int maxRegisters) {
        double winnerOfTheWorldWeight = Double.MAX_VALUE;
        List<InstructionMark> bestProgram = null;

        for (int maxSizePopulation = startPopulation; maxSizePopulation < maxPopulation; maxSizePopulation += 10000) {
            for (double curpopularParents = minPopularParents; curpopularParents < maxPopularParents; curpopularParents += 0.2) {
                for (int curMaxRegisters = minRegisters; curMaxRegisters < maxRegisters; curMaxRegisters++) {
                    List<TestcaseInOutParameters> collection = source.getInOutParameters(curMaxRegisters);

                    ProgramFitnessExaminerInterface evaluator = new ProgramFitnessExaminer(collection, new RegularStatementRunner());

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

    public RandomGeneticProgramIterator(ProgramFitnessExaminerInterface evaluator, RegularInstructionOpcodeEnum[] enums, int maxPopulation, double maxOverflow, double popularParents) {
        this.evaluator = evaluator;
        this.enums = enums;
        this.maxPopulation = maxPopulation;
        this.maxOverflow = maxOverflow;
        this.popularParents = popularParents;
        chosenSolutions = new ArrayList<>();
    }

    public ProgramResolution iterate(int numberOfRegisters, int maximumInstructions) {
        chosenSolutions = new ArrayList<>();
        this.numberOfRegisters = numberOfRegisters;
        this.maximumInstructions = maximumInstructions;
        registers = Register.createRegisters(numberOfRegisters).toArray(new Register[0]);
        Set<Register> availableRegisters = new HashSet<>();
        availableRegisters.add(registers[registers.length - 1]);// Add the result register.
        for (int i = 0; i < 1000; i++) {
            createRandomInstructionRecursively(new ArrayList<>());
        }
       // System.out.println(chosenSolutions);
        PriorityQueue<ProgramResolution> solutions = new PriorityQueue<>();
        for (List<InstructionMark> instruction : chosenSolutions)
        {
            solutions.add(new ProgramResolution(instruction, eval(instruction, Arrays.asList(registers))));
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

        for (List<InstructionMark> childDNA : mom.procreate(dad, 3))
        {
            solutions.add(new ProgramResolution(childDNA, eval(childDNA, Arrays.asList(registers))));
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

    public void createRandomInstructionRecursively(List<InstructionMark> instructions) {
        if (instructions.size() >= maximumInstructions)
        {
            chosenSolutions.add(new ArrayList<>(instructions));
            return;
        }

        Random r = new Random();
        int location = r.nextInt(enums.length);
        RegularInstructionOpcodeEnum instruction = enums[location];
        Register register1 = registers[r.nextInt(registers.length)];
        if (instruction.isDualRegister()) {
            Register register2 = registers[r.nextInt(registers.length)];

            if (isUselessOp(instruction, register1, register2)) {
                return;
            }

            InstructionMark actualInstruction = instructionFactory.createInstruction(new laboflieven.common.RegularInstructionOpcode(instruction), register1, register2);
            instructions.add(actualInstruction);
            //eval(instructions, Arrays.asList(registers));
        } else {

            InstructionMark actualInstruction = instructionFactory.createInstruction(new laboflieven.common.RegularInstructionOpcode(instruction), register1);
            instructions.add(0, actualInstruction);
            //eval(instructions, Arrays.asList(registers));
            /**
             * Available registers remains the same. No new registers are used.
             */

        }
        createRandomInstructionRecursively(instructions);
        instructions.remove(0);
    }

    private boolean isUselessOp(RegularInstructionOpcodeEnum instruction, Register register1, Register register2) {
        return instruction == RegularInstructionOpcodeEnum.Move && register1.name.equals(register2.name);
    }

    private double eval(List<InstructionMark> instructions, List<Register> registers) {
        return evaluator.calculateFitness(instructions, registers);
    }

}
