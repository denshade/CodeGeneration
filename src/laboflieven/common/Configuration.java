package laboflieven.common;

import laboflieven.accinstructions.AccInstructionOpcodeEnum;
import laboflieven.accinstructions.AccInstructionOpcodeEnumBuilder;
import laboflieven.accinstructions.InstructionFactory;
import laboflieven.challenges.ProgramTemplate;
import laboflieven.examiners.ProgramFitnessExaminerInterface;
import laboflieven.programiterators.*;
import laboflieven.recursionheuristics.AccHeuristic;
import laboflieven.recursionheuristics.AlwaysRecursionHeuristic;
import laboflieven.recursionheuristics.RecursionHeuristic;
import laboflieven.statements.InstructionFactoryInterface;
import laboflieven.statements.RegularInstructionOpcodeEnum;

import java.util.HashMap;
import java.util.Set;

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
    private static class StringParser implements Parser {

        @Override
        public Object parse(String s) {
            return s;
        }
    }

    private static class BoolParser implements Parser {

        @Override
        public Object parse(String s) {
            return Boolean.parseBoolean(s);
        }
    }

    private static class InstructionFactoryParser implements Parser {

        @Override
        public Object parse(String s) {
            switch(s) {
                case "Regular" : return new laboflieven.statements.InstructionFactory();
                case "Acc" :
                default:
                    System.out.println("Warning: Didn't recognize instruction factory:" + s);
                    return new InstructionFactory();
            }
        }
    }
    private static class ProgramIteratorParser implements Parser {

        @Override
        public Object parse(String s) {
            return switch (s) {
                case "random" -> new RandomProgramIterator();
                case "genetic" -> new GeneralRandomGeneticProgramIterator();
                case "brute" -> new GeneralBruteForceProgramIterator();
                case "priority" -> new AccPriorityProgramIterator();
                default -> new RandomProgramIterator();
            };
        }
    }

    private static class DataProviderParser implements Parser {
        @Override
        public ProgramTemplate parse(String s) {
            try {
                return (ProgramTemplate) Class.forName(s).newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static class HeuristicParser implements Parser {
        @Override
        public RecursionHeuristic parse(String s) {
            return switch (s) {
                case "Acc" -> new AccHeuristic();
                default -> new AlwaysRecursionHeuristic();
            };
        }
    }

    private static class AccOperationsParser implements Parser {
        @Override
        public AccInstructionOpcodeEnum[] parse(String s) {
            return switch (s) {
                case "nobranch" -> AccInstructionOpcodeEnumBuilder.make().anyExcept(
                        Set.of(
                                new AccInstructionOpcodeEnum[]{
                                        AccInstructionOpcodeEnum.JumpIfGteStart,
                                        AccInstructionOpcodeEnum.JumpIfLteStart,
                                        AccInstructionOpcodeEnum.Jump2IfEq,
                                        AccInstructionOpcodeEnum.Jump2IfGte,
                                        AccInstructionOpcodeEnum.Jump2IfLte,
                                        AccInstructionOpcodeEnum.Jump2IfNeq,
                                        AccInstructionOpcodeEnum.Jump2IfZero,
                                })).build();
                default -> AccInstructionOpcodeEnumBuilder.make().fromString(s).build();
            };
        }
    }
    private static class DataSourceParser implements Parser {
        @Override
        public Object parse(String s) {
            try {
                return Class.forName(s).newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
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
        MAX_ERROR_VALUE(new DoubleParser()),
        DATA_PROVIDER(new DataProviderParser()),
        MAX_POPULATION(new IntParser()),
        MAX_OVERFLOW(new DoubleParser()),
        POPULAR_PARENT_PART(new DoubleParser()),
        RECURSION_HEURISTIC(new HeuristicParser()),
        ACC_OPERATIONS(new AccOperationsParser()),
        CUT_POPULATION_AT_MAX(new IntParser()),
        CUT_POPULATION_TO(new IntParser()), RND_ADDED(new BoolParser()),
        ERROR_TOLERANCE(new DoubleParser()),
        DATA_SOURCE(new DataSourceParser()),
        CSV_FILE(new StringParser());

        public Parser parser;

        ConfigurationKey(Parser s) {
            this.parser = s;
        }

    }

    public String getHelp()
    {
        var buffer = new StringBuilder();
        for (Configuration.ConfigurationKey key : Configuration.ConfigurationKey.values()) {
            buffer.append(key.name()).append(" ").append(key.parser.getClass());
        }
        return buffer.toString();
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

    public String getCsvFile(String defaultFilePath)
    {
        return getValue(defaultFilePath, ConfigurationKey.CSV_FILE);
    }

    public Configuration setMaxNrInstructions(int nrInstructions) {
        configurationSettings.put(ConfigurationKey.MAX_NR_OF_INSTRUCTIONS, nrInstructions);
        return this;
    }

    public int getNumberOfRegisters(int defaultValue) {
        return getValue(defaultValue, ConfigurationKey.NR_REGISTERS);
    }

    public Configuration setNumberOfRegisters(int value) {
        configurationSettings.put(ConfigurationKey.NR_REGISTERS, value);
        return this;
    }

    private int getValue(int defaultValue, ConfigurationKey maxNrOfInstructions) {
        if (!configurationSettings.containsKey(maxNrOfInstructions)) {
            return defaultValue;
        }
        return (int) configurationSettings.get(maxNrOfInstructions);
    }

    private String getValue(String defaultValue, ConfigurationKey key) {
        return configurationSettings.getOrDefault(key, defaultValue).toString();
    }

    private double getValue(double defaultValue, ConfigurationKey key) {
        return (double) configurationSettings.getOrDefault(key, defaultValue);
    }

    public int getMaxDurationSeconds(int defaultValue) {
        return getValue(defaultValue, ConfigurationKey.MAX_DURATION_SECONDS);
    }

    public Configuration setFitnessExaminer(ProgramFitnessExaminerInterface programFitnessExaminer)
    {
        configurationSettings.put(ConfigurationKey.FITNESS_EXAMINER, programFitnessExaminer);
        return this;
    }

    public ProgramFitnessExaminerInterface getFitnessExaminer() {
        return (ProgramFitnessExaminerInterface) configurationSettings.get(ConfigurationKey.FITNESS_EXAMINER);
    }

    public Configuration setInstructionFactory(InstructionFactory instructionFactory) {
        configurationSettings.put(ConfigurationKey.INSTRUCTION_FACTORY, instructionFactory);
        return this;
    }

    public InstructionFactoryInterface getInstructionFactory(InstructionFactory defaultFactory)
    {
        return (InstructionFactoryInterface) configurationSettings.getOrDefault(ConfigurationKey.INSTRUCTION_FACTORY, defaultFactory);
    }

    public void setInstructionOpcodes(RegularInstructionOpcodeEnum[] regularInstructionOpcodeEnums) {
        configurationSettings.put(ConfigurationKey.OPCODES, regularInstructionOpcodeEnums);
    }

    public RegularInstructionOpcodeEnum[] getInstructionOpcodes()
    {
        return (RegularInstructionOpcodeEnum[]) configurationSettings.get(ConfigurationKey.OPCODES);
    }

    public ProgramIterator getProgramIterator(ProgramIterator defaultIterator) {
        return (ProgramIterator) configurationSettings.getOrDefault(ConfigurationKey.PROGRAM_ITERATOR, defaultIterator);
    }

    public ProgramTemplate getDataProvider() {
        return (ProgramTemplate) configurationSettings.get(ConfigurationKey.DATA_PROVIDER);
    }

    public int getMaxPopulation(int defaultValue) {
        return (int) configurationSettings.getOrDefault(ConfigurationKey.MAX_POPULATION, defaultValue);
    }

    public void setMaxPopulation(int maxPopulation) {
        configurationSettings.put(ConfigurationKey.MAX_POPULATION, maxPopulation);
    }

    public double getMaxOverFlow(double overflow) {
        return (double) configurationSettings.getOrDefault(ConfigurationKey.MAX_OVERFLOW, overflow);
    }

    public void setMaxOverflow(double maxOverflow) {
        configurationSettings.put(ConfigurationKey.MAX_OVERFLOW, maxOverflow);
    }


    public double getPopularParents(double popularParents) {
        return (double) configurationSettings.getOrDefault(ConfigurationKey.POPULAR_PARENT_PART, popularParents);
    }

    public void setPopularParents(double popularParents) {
        configurationSettings.put(ConfigurationKey.POPULAR_PARENT_PART, popularParents);
    }


    public RecursionHeuristic getHeuristic(AlwaysRecursionHeuristic alwaysRecursionHeuristic) {
        return (RecursionHeuristic) configurationSettings.getOrDefault(ConfigurationKey.RECURSION_HEURISTIC, alwaysRecursionHeuristic);
    }

    public AccInstructionOpcodeEnum[] getAccOperations() {
        return (AccInstructionOpcodeEnum[]) configurationSettings.getOrDefault(ConfigurationKey.ACC_OPERATIONS, AccInstructionOpcodeEnum.values());
    }
    public Configuration setAccOperations(AccInstructionOpcodeEnum[] enums) {
        configurationSettings.put(ConfigurationKey.ACC_OPERATIONS, enums);
        return this;
    }

    public int getCutPopulationAtMax(int defaultNr){
        return getValue(defaultNr, ConfigurationKey.CUT_POPULATION_AT_MAX);
    }
    public void setCutPopulationAtMax(int defaultNr){
        configurationSettings.put(ConfigurationKey.CUT_POPULATION_AT_MAX, defaultNr);
    }

    public int getCutPopulationTo(int defaultNr){
        return getValue(defaultNr, ConfigurationKey.CUT_POPULATION_TO);
    }

    void setByKey(ConfigurationKey key, String value)
    {
        configurationSettings.put(key, key.parser.parse(value));
    }

    public String toString()
    {
        return configurationSettings.toString();
    }

    public boolean getRandomAdded(boolean defaultBool) {
        return (boolean) configurationSettings.getOrDefault(ConfigurationKey.RND_ADDED, defaultBool);
    }
    public void setRandomAdded(boolean bool) {
         configurationSettings.put(ConfigurationKey.RND_ADDED, bool);
    }

    public Configuration setHeuristic(RecursionHeuristic heuristic) {
        configurationSettings.put(ConfigurationKey.RECURSION_HEURISTIC, heuristic);
        return this;
    }


    public double getErrorTolerance(double defaultErrorTolerance) {
        return (double) configurationSettings.getOrDefault(ConfigurationKey.ERROR_TOLERANCE, defaultErrorTolerance);
    }
    public Configuration setErrorTolerance(double v) {
        configurationSettings.put(ConfigurationKey.ERROR_TOLERANCE, v);
        return this;
    }



}
