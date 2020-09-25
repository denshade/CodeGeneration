package laboflieven.common;

import laboflieven.accinstructions.InstructionFactory;
import laboflieven.examiners.ProgramFitnessExaminerInterface;
import laboflieven.statements.InstructionFactoryInterface;
import laboflieven.statements.RegularInstructionOpcodeEnum;

import java.util.HashMap;

public class Configuration {

    private static final String OPCODES = "OPCODES";
    private static String MAX_NR_OF_INSTRUCTIONS = "max_nr_instructions";
    private static String NR_REGISTERS = "nr_registers";
    private static String MAX_DURATION_SECONDS = "max_duration_seconds";
    private static String FITNESS_EXAMINER = "fitness_examiner";
    private static final String INSTRUCTION_FACTORY = "INSTRUCTION_FACTORY";
    private HashMap<String, Object> configurationSettings = new HashMap<>();

    private static Configuration instance;
    public static Configuration getInstance()
    {
        if (instance == null) {
            instance = new Configuration();
        }
        return instance;
    }


    public int getMaxNrInstructions(int defaultValue)
    {
        return getValue(defaultValue, MAX_NR_OF_INSTRUCTIONS);
    }

    public void setMaxNrInstructions(int nrInstructions) {
        configurationSettings.put(MAX_NR_OF_INSTRUCTIONS, nrInstructions);
    }

    public int getNumberOfRegisters(int defaultValue) {
        return getValue(defaultValue, NR_REGISTERS);
    }

    public void setNumberOfRegisters(int value) {
        configurationSettings.put(NR_REGISTERS, value);
    }

    private int getValue(int defaultValue, String maxNrOfInstructions) {
        if (!configurationSettings.containsKey(maxNrOfInstructions)) {
            return defaultValue;
        }
        return (int) configurationSettings.get(maxNrOfInstructions);
    }

    public void setMaxDurationSeconds(int maxDurationSeconds)
    {
        configurationSettings.put(MAX_DURATION_SECONDS, maxDurationSeconds);
    }

    public int getMaxDurationSeconds(int defaultValue) {
        return getValue(defaultValue, NR_REGISTERS);
    }

    public void setFitnessExaminer(ProgramFitnessExaminerInterface programFitnessExaminer)
    {
        configurationSettings.put(FITNESS_EXAMINER, programFitnessExaminer);
    }

    public ProgramFitnessExaminerInterface getFitnessExaminer() {
        return (ProgramFitnessExaminerInterface) configurationSettings.get(FITNESS_EXAMINER);
    }

    public void setInstructionFactory(InstructionFactory instructionFactory) {
        configurationSettings.put(INSTRUCTION_FACTORY, instructionFactory);
    }

    public InstructionFactoryInterface getInstructionFactory()
    {
        return (InstructionFactoryInterface) configurationSettings.get(INSTRUCTION_FACTORY);
    }

    public void setInstructionOpcodes(RegularInstructionOpcodeEnum[] regularInstructionOpcodeEnums) {
        configurationSettings.put(OPCODES, regularInstructionOpcodeEnums);
    }

    public RegularInstructionOpcodeEnum[] getInstructionOpcodes()
    {
        return (RegularInstructionOpcodeEnum[]) configurationSettings.get(OPCODES);
    }
}
