package laboflieven.common;

import laboflieven.accinstructions.InstructionFactory;
import laboflieven.examiners.ProgramFitnessExaminerInterface;
import laboflieven.programiterators.ProgramIterator;
import laboflieven.programiterators.RandomProgramIterator;
import laboflieven.statements.InstructionFactoryInterface;
import laboflieven.statements.RegularInstructionOpcodeEnum;

import java.util.HashMap;

public class Configuration {


    private interface Parser
    {
        Object parse(String s);
    }
    private static class IntParser implements Parser {

        @Override
        public Object parse(String s) {
            return Integer.parseInt(s);
        }
    }
    private static class DoubleParser implements Parser {

        @Override
        public Object parse(String s) {
            return Double.parseDouble(s);
        }
    }
    private static class InstructionFactoryParser implements Parser {

        @Override
        public Object parse(String s) {
            switch(s) {
                case "Regular" : return new laboflieven.statements.InstructionFactory();
                case "Acc" :
                default: return new InstructionFactory();
            }
        }
    }
    private static class ProgramIteratorParser implements Parser {

        @Override
        public Object parse(String s) {
            switch(s) {
                case "Random" : return new RandomProgramIterator();
                case "Acc" :
                default: return new InstructionFactory();
            }
        }
    }

    public enum ConfigurationKey  {
        OPCODES(new IntParser()),
        MAX_NR_OF_INSTRUCTIONS(new IntParser()),
        NR_REGISTERS(new IntParser()),
        MAX_DURATION_SECONDS(new IntParser()),
        FITNESS_EXAMINER(new IntParser()),
        INSTRUCTION_FACTORY(new InstructionFactoryParser()),
        PROGRAM_ITERATOR(new ProgramIteratorParser()),
        MAX_ERROR_VALUE(new DoubleParser());

        public Parser parser;

        ConfigurationKey(Parser s) {
            this.parser = s;
        }
    }


    private HashMap<ConfigurationKey, Object> configurationSettings = new HashMap<>();

    private static Configuration instance;
    public static Configuration getInstance()
    {
        if (instance == null) {
            instance = new Configuration();
        }
        return instance;
    }

    public Double getMaxError(double defaultValue)
    {
        return getValue(defaultValue, ConfigurationKey.MAX_ERROR_VALUE);
    }

    public int getMaxNrInstructions(int defaultValue)
    {
        return getValue(defaultValue, ConfigurationKey.MAX_NR_OF_INSTRUCTIONS);
    }

    public void setMaxNrInstructions(int nrInstructions) {
        configurationSettings.put(ConfigurationKey.MAX_NR_OF_INSTRUCTIONS, nrInstructions);
    }

    public int getNumberOfRegisters(int defaultValue) {
        return getValue(defaultValue, ConfigurationKey.NR_REGISTERS);
    }

    private int getValue(int defaultValue, ConfigurationKey maxNrOfInstructions) {
        if (!configurationSettings.containsKey(maxNrOfInstructions)) {
            return defaultValue;
        }
        return (int) configurationSettings.get(maxNrOfInstructions);
    }

    private double getValue(double defaultValue, ConfigurationKey maxNrOfInstructions) {
        if (!configurationSettings.containsKey(maxNrOfInstructions)) {
            return defaultValue;
        }
        return (double) configurationSettings.get(maxNrOfInstructions);
    }

    public int getMaxDurationSeconds(int defaultValue) {
        return getValue(defaultValue, ConfigurationKey.NR_REGISTERS);
    }

    public void setFitnessExaminer(ProgramFitnessExaminerInterface programFitnessExaminer)
    {
        configurationSettings.put(ConfigurationKey.FITNESS_EXAMINER, programFitnessExaminer);
    }

    public ProgramFitnessExaminerInterface getFitnessExaminer() {
        return (ProgramFitnessExaminerInterface) configurationSettings.get(ConfigurationKey.FITNESS_EXAMINER);
    }

    public void setInstructionFactory(InstructionFactory instructionFactory) {
        configurationSettings.put(ConfigurationKey.INSTRUCTION_FACTORY, instructionFactory);
    }

    public InstructionFactoryInterface getInstructionFactory()
    {
        return (InstructionFactoryInterface) configurationSettings.get(ConfigurationKey.INSTRUCTION_FACTORY);
    }

    public void setInstructionOpcodes(RegularInstructionOpcodeEnum[] regularInstructionOpcodeEnums) {
        configurationSettings.put(ConfigurationKey.OPCODES, regularInstructionOpcodeEnums);
    }

    public RegularInstructionOpcodeEnum[] getInstructionOpcodes()
    {
        return (RegularInstructionOpcodeEnum[]) configurationSettings.get(ConfigurationKey.OPCODES);
    }

    public ProgramIterator getProgramIterator(ProgramIterator defaultIterator) {
        if (!configurationSettings.containsKey(ConfigurationKey.PROGRAM_ITERATOR)) {
            return defaultIterator;
        }
        return (ProgramIterator) configurationSettings.get(ConfigurationKey.PROGRAM_ITERATOR);
    }


    void setByKey(ConfigurationKey key, String value)
    {
        configurationSettings.put(key, key.parser.parse(value));
    }

    public String toString()
    {
        return configurationSettings.toString();
    }
}
