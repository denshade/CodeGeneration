package laboflieven.programiterators;

import laboflieven.InstructionMark;
import laboflieven.ProgramResolution;
import laboflieven.accinstructions.LoadAccLeftIntoRegister;
import laboflieven.accinstructions.LoadIntoLeftAcc;
import laboflieven.common.ArrayListBestFitRegister;
import laboflieven.common.BestFitRegister;
import laboflieven.common.Configuration;
import laboflieven.examiners.ProgramFitnessExaminerInterface;
import laboflieven.StoppedByUserException;
import laboflieven.statements.*;

import java.util.*;

/**
 * Created by lveeckha on 31/05/2015.
 */
public class RandomProgramIterator implements ProgramIterator {
    public int maximumInstructions = 12;
    public long counter = 0;

    public List<List<InstructionMark>> positiveSolutions = new ArrayList<>();
    private ProgramFitnessExaminerInterface evaluator;
    private int maxExecutionTimeSeconds = 3600 * 12;
    private RegularInstructionOpcodeEnum[] enums;
    private Register[] registers;
    private ArrayListBestFitRegister bestFit = new ArrayListBestFitRegister();
    private int numberOfRegisters;
    public InstructionFactoryInterface instructionFactory = new InstructionFactory();

    @Override
    public ProgramResolution iterate(Configuration configuration) {
        System.out.println("Running with " + configuration);
        this.evaluator = configuration.getFitnessExaminer();
        this.numberOfRegisters = configuration.getNumberOfRegisters(2);
        this.maximumInstructions = configuration.getMaxNrInstructions(6);
        this.instructionFactory = configuration.getInstructionFactory();
        this.enums = configuration.getInstructionOpcodes();
        registers = Register.createRegisters(numberOfRegisters, "R").toArray(new Register[0]);
        long startTime = System.currentTimeMillis();
        long runTime = System.currentTimeMillis() - startTime;
        while (runTime < configuration.getMaxDurationSeconds(3600) * 1000) {
            loop();
            runTime = System.currentTimeMillis() - startTime;
        }
        return null;
    }

    public void loop() {
        var instructions = new ArrayList<InstructionMark>();
        instructions.add(new LoadIntoLeftAcc(registers[0]));
        for (int i = 0; i < maximumInstructions - 2; i++)
        {
            InstructionMark actualInstruction = instructionFactory.generateRandomInstruction(Arrays.asList(registers));
            instructions.add(actualInstruction);
        }
        instructions.add(new LoadAccLeftIntoRegister(registers[0]));
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
