package laboflieven.programiterators;

import laboflieven.InstructionMark;
import laboflieven.ProgramResolution;
import laboflieven.instructions.accinstructions.LoadAccLeftIntoRegister;
import laboflieven.instructions.accinstructions.LoadIntoLeftAcc;
import laboflieven.common.BestFitRegister;
import laboflieven.common.Configuration;
import laboflieven.examiners.ProgramFitnessExaminerInterface;
import laboflieven.StoppedByUserException;
import laboflieven.instructions.regular.InstructionFactory;
import laboflieven.instructions.regular.InstructionFactoryInterface;
import laboflieven.instructions.regular.RegularInstructionOpcodeEnum;
import laboflieven.registers.Register;

import java.util.*;

/**
 * Created by lveeckha on 31/05/2015.
 */
public class BestPartialProgramCombinationIterator implements ProgramIterator {
    public int maximumInstructions = 12;
    public long counter = 0;

    public List<List<InstructionMark>> positiveSolutions = new ArrayList<>();
    private ProgramFitnessExaminerInterface evaluator;
    private final int maxExecutionTimeSeconds = 3600 * 12;
    private RegularInstructionOpcodeEnum[] enums;
    private Register[] registers;
    private final BestFitRegister<List<InstructionMark>> bestFit = new BestFitRegister<>();
    private int numberOfRegisters;
    public InstructionFactoryInterface instructionFactory = new InstructionFactory();

    @Override
    public ProgramResolution iterate(Configuration configuration) {
        System.out.println("Running with " + configuration);
        this.evaluator = configuration.getFitnessExaminer();
        this.numberOfRegisters = configuration.getNumberOfRegisters(2);
        this.maximumInstructions = configuration.getMaxNrInstructions(6);
        this.instructionFactory = configuration.getInstructionFactory(new laboflieven.instructions.accinstructions.InstructionFactory());
        this.enums = configuration.getInstructionOpcodes();
        registers = configuration.getNamingScheme().createRegisters(numberOfRegisters).toArray(new Register[0]);
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
