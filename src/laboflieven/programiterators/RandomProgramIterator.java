package laboflieven.programiterators;

import laboflieven.InstructionMark;
import laboflieven.accinstructions.AccLeftPull;
import laboflieven.accinstructions.AccLeftPush;
import laboflieven.common.BestFitRegister;
import laboflieven.examiners.ProgramFitnessExaminer;
import laboflieven.examiners.ProgramFitnessExaminerInterface;
import laboflieven.StoppedByUserException;
import laboflieven.statements.*;

import java.util.*;

/**
 * Created by lveeckha on 31/05/2015.
 */
public class RandomProgramIterator {
    public int maximumInstructions = 12;
    public long counter = 0;

    public List<List<InstructionMark>> positiveSolutions = new ArrayList<>();
    private ProgramFitnessExaminerInterface evaluator;
    private int maxExecutionTimeSeconds = 3600 * 2;
    private RegularInstructionOpcodeEnum[] enums;
    private Register[] registers;
    private BestFitRegister<List<InstructionMark>> bestFit = new BestFitRegister<>();
    private int numberOfRegisters;
    private List<InstructionMark> bestSolution;
    private double bestScore = 1000;
    public InstructionFactoryInterface instructionFactory = new InstructionFactory();

    public RandomProgramIterator(ProgramFitnessExaminerInterface evaluator) {
        this.evaluator = evaluator;
        enums = RegularInstructionOpcodeEnum.values();
    }

    public RandomProgramIterator(ProgramFitnessExaminerInterface evaluator, RegularInstructionOpcodeEnum[] enums) {
        this.evaluator = evaluator;

        this.enums = enums;
    }

    public RandomProgramIterator(ProgramFitnessExaminer evaluator, int maxExecutionTime) {
        this.evaluator = evaluator;
        this.maxExecutionTimeSeconds = maxExecutionTime;
        enums = RegularInstructionOpcodeEnum.values();

    }

    public void iterate(int numberOfRegisters, int maximumInstructions) {
        this.numberOfRegisters = numberOfRegisters;
        this.maximumInstructions = maximumInstructions;
        registers = Register.createRegisters(numberOfRegisters, "R").toArray(new Register[0]);
        long startTime = System.currentTimeMillis();
        long runTime = System.currentTimeMillis() - startTime;
        while (runTime < maxExecutionTimeSeconds * 1000) {
            loop();
            runTime = System.currentTimeMillis() - startTime;
        }
    }
    public void loop() {
        var instructions = new ArrayList<InstructionMark>();
        instructions.add(new AccLeftPush(registers[0]));
        for (int i = 0; i < maximumInstructions - 2; i++)
        {
            InstructionMark actualInstruction = instructionFactory.generateRandomInstruction(Arrays.asList(registers));
            instructions.add(actualInstruction);
        }
        instructions.add(new AccLeftPull(registers[0]));
        eval(instructions, Arrays.asList(registers));
    }
    private void eval(List<InstructionMark> instructions, List<Register> registers) {
        double val =  evaluator.calculateFitness(instructions, registers);
        bestFit.register(val, instructions);
        if (instructions.size() == maximumInstructions && val < 0.0001) {
            positiveSolutions.add(new ArrayList<>(instructions));
            System.out.println("Found a program! " + positiveSolutions);
            throw new StoppedByUserException();
        }

    }

    public static int difference(List<InstructionMark> instructions) {
        Map<String, Integer> counter = new HashMap<>();
        for (InstructionMark instruction : instructions)
        {
            counter.putIfAbsent(instruction.getClass().toString(), 0);
            Integer count = counter.get(instruction.getClass().toString());
            counter.put(instruction.getClass().toString(), count + 1);
        }
        return counter.keySet().size();
    }

}
