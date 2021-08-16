package laboflieven.common;

import laboflieven.accinstructions.AccInstructionOpcodeEnum;
import laboflieven.accinstructions.InstructionFactory;
import laboflieven.challenges.ProgramTemplate;
import laboflieven.examiners.ProgramFitnessExaminerInterface;
import laboflieven.programiterators.*;
import laboflieven.recursionheuristics.AccHeuristic;
import laboflieven.recursionheuristics.AlwaysRecursionHeuristic;
import laboflieven.recursionheuristics.HashedResultsHeuristic;
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
            switch(s) {
                case "Random" : return new RandomProgramIterator();
                case "genetic" : return new GeneralRandomGeneticProgramIterator();
                case "brute" : return new GeneralBruteForceProgramIterator();
                case "priority": return new AccPriorityProgramIterator();
                default: return new RandomProgramIterator();
            }
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
                switch(s)
                {
                    case "Acc": return new AccHeuristic();
                    case "always":
                    default: return new AlwaysRecursionHeuristic();
                }
        }
    }

    private static class AccOperationsParser implements Parser {
        @Override
        public AccInstructionOpcodeEnum[] parse(String s) {
            switch(s)
            {
                case "nobranch": return AccInstructionOpcodeEnum.anyExcept(
                        Set.of(
                            new AccInstructionOpcodeEnum[]{
                                    AccInstructionOpcodeEnum.JumpIfGteStart,
                                    AccInstructionOpcodeEnum.JumpIfLteStart,
                                    AccInstructionOpcodeEnum.Jump2IfEq,
                                    AccInstructionOpcodeEnum.Jump2IfGte,
                                    AccInstructionOpcodeEnum.Jump2IfLte,
                                    AccInstructionOpcodeEnum.Jump2IfNeq,
                                    AccInstructionOpcodeEnum.Jump2IfZero,
                            }));
                case "all":
                default: return AccInstructionOpcodeEnum.values();
            }
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
        DATA_SOURCE(new DataSourceParser());

        public Parser parser;

        ConfigurationKey(Parser s) {
            this.parser = s;
        }

    }

    public String getHelp()
    {
        var buffer = new StringBuffer();
        for (Configuration.ConfigurationKey key : Configuration.ConfigurationKey.values()) {
            buffer.append(key.name()+" " + key.parser.getClass());
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

    private double getValue(double defaultValue, ConfigurationKey maxNrOfInstructions) {
        if (!configurationSettings.containsKey(maxNrOfInstructions)) {
            return defaultValue;
        }
        return (double) configurationSettings.get(maxNrOfInstructions);
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

    public ProgramTemplate getDataProvider() {
        return (ProgramTemplate) configurationSettings.get(ConfigurationKey.DATA_PROVIDER);
    }

    public int getMaxPopulation() {
        return (int) configurationSettings.get(ConfigurationKey.MAX_POPULATION);
    }

    public void setMaxPopulation(int maxPopulation) {
        configurationSettings.put(ConfigurationKey.MAX_POPULATION, maxPopulation);
    }

    public double getMaxOverFlow() {
        return (double) configurationSettings.get(ConfigurationKey.MAX_OVERFLOW);
    }

    public void setMaxOverflow(double maxOverflow) {
        configurationSettings.put(ConfigurationKey.MAX_OVERFLOW, maxOverflow);
    }


    public double getPopularParents() {
        return (double) configurationSettings.get(ConfigurationKey.POPULAR_PARENT_PART);
    }

    public void setPopularParents(double popularParents) {
        configurationSettings.put(ConfigurationKey.POPULAR_PARENT_PART, popularParents);
    }


    public RecursionHeuristic getHeuristic(AlwaysRecursionHeuristic alwaysRecursionHeuristic) {
        if (configurationSettings.containsKey(ConfigurationKey.RECURSION_HEURISTIC))
                return (RecursionHeuristic) configurationSettings.get(ConfigurationKey.RECURSION_HEURISTIC);
            else
                return alwaysRecursionHeuristic;
    }

    public AccInstructionOpcodeEnum[] getAccOperations() {
        if (configurationSettings.containsKey(ConfigurationKey.ACC_OPERATIONS))
            return (AccInstructionOpcodeEnum[]) configurationSettings.get(ConfigurationKey.ACC_OPERATIONS);
        else
            return AccInstructionOpcodeEnum.values();
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
        if (!configurationSettings.containsKey(ConfigurationKey.RND_ADDED)) {
            return defaultBool;
        }
        return (boolean) configurationSettings.get(ConfigurationKey.RND_ADDED);
    }
    public void setRandomAdded(boolean bool) {
         configurationSettings.put(ConfigurationKey.RND_ADDED, bool);
    }

    public Configuration setHeuristic(RecursionHeuristic heuristic) {
        configurationSettings.put(ConfigurationKey.RECURSION_HEURISTIC, heuristic);
        return this;
    }


}
