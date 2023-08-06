package laboflieven.programiterators;

import laboflieven.InstructionMark;
import laboflieven.ProgramResolution;
import laboflieven.accinstructions.*;
import laboflieven.common.AccInstructionOpcode;
import laboflieven.common.BestFitRegister;
import laboflieven.common.Configuration;
import laboflieven.common.PriorityQueueAlgos;
import laboflieven.examiners.ProgramFitnessExaminerInterface;
import laboflieven.recursionheuristics.AlwaysRecursionHeuristic;
import laboflieven.recursionheuristics.RecursionHeuristic;
import laboflieven.statements.InstructionFactoryInterface;
import laboflieven.registers.Register;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.logging.Logger;

/**
 * Created by Lieven on 11-6-2016.
 */
public class PredefinedStartAndEndAccPriorityProgramIterator implements ProgramIterator
{
    private final Logger LOGGER = Logger.getLogger(PredefinedStartAndEndAccPriorityProgramIterator.class.getName());
    private ProgramFitnessExaminerInterface evaluator;
    private AccInstructionOpcodeEnum[] enums;
    PriorityQueue<ProgramResolution> priorityQueue = new PriorityQueue<>();
    private List<Register> registers;
    private RecursionHeuristic heuristic = new AlwaysRecursionHeuristic();
    private InstructionFactoryInterface instructionFactory;
    private final BestFitRegister<ProgramResolution> bestFitRegister = new BestFitRegister<>();


    public ProgramResolution iterate(Configuration configuration)
    {
        this.evaluator = configuration.getFitnessExaminer();
        this.enums = configuration.getAccOperations();
        this.heuristic = configuration.getHeuristic(new AlwaysRecursionHeuristic());
        this.instructionFactory = configuration.getInstructionFactory(new InstructionFactory());
        if (instructionFactory == null) throw new IllegalArgumentException("Instruction Factory shouldn't be empty");
        registers = Register.createRegisters(configuration.getNumberOfRegisters(2));
        int CUT_POPULATION_AT_MAX = configuration.getCutPopulationAtMax(150000);
        int CUT_POPULATION_TO = configuration.getCutPopulationTo(100000);
        boolean addRandom = configuration.getRandomAdded(true);
        int maxInstructions = configuration.getMaxNrInstructions(10);
        maxInstructions = remove2PrefixAnd2SuffixCount(maxInstructions);
        List<InstructionMark> initialProgram = new ArrayList<>();
        initialProgram.add(new LoadIntoLeftAcc(new Register("R1")));
        initialProgram.add(new LoadIntoRightAcc(new Register("R2")));
        addLevel(registers, initialProgram, maxInstructions);
        while (priorityQueue.size() > 0)
        {
            ProgramResolution res = priorityQueue.poll();
            List<InstructionMark> instructions = res.instructions;
            if (instructions.size() < maxInstructions) {
                ProgramResolution score = eval(instructions, registers);
                //if (score.weight < 1000000) {
                addLevel(registers, instructions, maxInstructions);
                //}
            } else {
                if (priorityQueue.size() > CUT_POPULATION_AT_MAX)
                {
                    LOGGER.warning("Cutting population");
                    priorityQueue = PriorityQueueAlgos.cutPopulation(CUT_POPULATION_TO, priorityQueue);
                }
            }
            if (addRandom)
            {
                createRandom(registers, res);
            }
            if (res.weight < 0.01) {
                return res;
            }
        }
        return prefixAndSuffix(bestFitRegister.getBest());
    }

    private ProgramResolution prefixAndSuffix(ProgramResolution best) {
        if (best != null) {
            best.instructions.add(new LoadAccLeftIntoRegister(registers.get(0)));
            best.instructions.add(new LoadAccRightIntoRegister(registers.get(1)));
        }
        return best;
    }

    private int remove2PrefixAnd2SuffixCount(int maxInstructions) {
        return maxInstructions - 4;
    }

    private void createRandom(List<Register> registerList, ProgramResolution res) {
        List<InstructionMark> randomProgram = new ArrayList<>();
        for (int k = 0; k < res.instructions.size(); k++) {
            randomProgram.add(instructionFactory.generateRandomInstruction(registerList));
        }
        ProgramResolution eval = eval(randomProgram, registerList);
        if (eval.weight / 2 < bestFitRegister.getBestScore())
        {
            priorityQueue.add(eval);
        }
    }

    private void addLevel(List<Register> registerList, List<InstructionMark> instructions, int maxInstructions) {

        for (AccInstructionOpcodeEnum opcode : enums) {
            AccInstructionOpcode opcodeR = new AccInstructionOpcode(opcode);
            if (opcodeR.getNrRegisters() == 0) {
                addToPriorityQueue(instructions, instructionFactory.createInstruction(opcodeR), registerList, maxInstructions);
            } else {
                for (Register r : registers) {
                    addToPriorityQueue(instructions, instructionFactory.createInstruction(opcodeR, r), registerList, maxInstructions);
                }
            }
        }
    }

    private void addToPriorityQueue(List<InstructionMark> instructions, InstructionMark instruction, List<Register> registerList, int maxInstructions) {
        List<InstructionMark> marks = new ArrayList<>(instructions);
        marks.add(instruction);
        if (instructions.size() == maxInstructions) {
            marks.add(new LoadAccLeftIntoRegister(registers.get(0)));
            marks.add(new LoadAccRightIntoRegister(registers.get(1)));
            ProgramResolution res = eval(marks, registerList);
            bestFitRegister.register(res.weight, res);
            priorityQueue.add(res);
        }
        else {
            ProgramResolution res = eval(marks, registerList);
            bestFitRegister.register(res.weight, res);
            priorityQueue.add(res);
        }
    }

    private ProgramResolution eval(List<InstructionMark> instructions, List<Register> registers) {
        var suffixedInstructions = new ArrayList<>(instructions);
        suffixedInstructions.add(new LoadAccLeftIntoRegister(registers.get(0)));
        suffixedInstructions.add(new LoadAccRightIntoRegister(registers.get(1)));
        double val = evaluator.calculateFitness(suffixedInstructions, registers);
        var resolution = new ProgramResolution(new ArrayList<>(instructions), val);
        bestFitRegister.register(val,  resolution);
        return resolution;
    }

}
