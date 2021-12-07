package laboflieven.programiterators;

import laboflieven.InstructionMark;
import laboflieven.Program;
import laboflieven.ProgramResolution;
import laboflieven.accinstructions.AccInstructionOpcodeEnum;
import laboflieven.accinstructions.InstructionFactory;
import laboflieven.common.AccInstructionOpcode;
import laboflieven.common.BestFitRegister;
import laboflieven.common.Configuration;
import laboflieven.common.PriorityQueueAlgos;
import laboflieven.examiners.ProgramFitnessExaminerInterface;
import laboflieven.recursionheuristics.AlwaysRecursionHeuristic;
import laboflieven.recursionheuristics.RecursionHeuristic;
import laboflieven.statements.InstructionFactoryInterface;
import laboflieven.statements.Register;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.logging.Logger;

/**
 * Created by Lieven on 11-6-2016.
 */
public class AccPriorityProgramIterator  implements ProgramIterator
{
    private Logger LOGGER = Logger.getLogger(AccPriorityProgramIterator.class.getName());
    private ProgramFitnessExaminerInterface evaluator;
    private AccInstructionOpcodeEnum[] enums;
    PriorityQueue<ProgramResolution> priorityQueue = new PriorityQueue<>();
    private List<Register> registers;
    private RecursionHeuristic heuristic = new AlwaysRecursionHeuristic();
    private InstructionFactoryInterface instructionFactory;
    private BestFitRegister<ProgramResolution> bestFitRegister = new BestFitRegister<>();


    public ProgramResolution iterate(Configuration configuration)
    {
        this.evaluator = configuration.getFitnessExaminer();
        this.enums = configuration.getAccOperations();
        this.instructionFactory = configuration.getInstructionFactory(new InstructionFactory());
        if (instructionFactory == null) throw new IllegalArgumentException("Instruction Factory shouldn't be empty");
        registers = Register.createRegisters(configuration.getNumberOfRegisters(2), "R");
        int CUT_POPULATION_AT_MAX = configuration.getCutPopulationAtMax(150000);
        int CUT_POPULATION_TO = configuration.getCutPopulationTo(100000);
        boolean addRandom = configuration.getRandomAdded(true);
        int maxInstructions = configuration.getMaxNrInstructions(10);
        addLevel(registers, new ArrayList<>(), maxInstructions);
        while (priorityQueue.size() > 0)
        {
            ProgramResolution res = priorityQueue.poll();
            List<InstructionMark> instructions = res.instructions;
            if (instructions.size() < maxInstructions) {
                ProgramResolution score = eval(instructions, registers);
                if (score.weight < 1000000) {
                    addLevel(registers, instructions, maxInstructions);
                }
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
        return null;
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
        Program p = new Program(instructions, registerList);
        if (!heuristic.shouldRecurse(p, maxInstructions))
        {
            return;
        }
        for (AccInstructionOpcodeEnum opcode : enums) {
            AccInstructionOpcode opcodeR = new AccInstructionOpcode(opcode);
            if (opcodeR.getNrRegisters() == 0) {
                addToPriorityQueue(instructions, instructionFactory.createInstruction(opcodeR), registerList);
            } else {
                for (Register r : registers) {
                    addToPriorityQueue(instructions, instructionFactory.createInstruction(opcodeR, r), registerList);
                }
            }
        }
    }

    private void addToPriorityQueue(List<InstructionMark> instructions, InstructionMark instruction, List<Register> registerList) {
        List<InstructionMark> marks = new ArrayList<>(instructions);
        marks.add(instruction);
        priorityQueue.add(eval(marks, registerList));
    }

    private ProgramResolution eval(List<InstructionMark> instructions, List<Register> registers) {
        double val = evaluator.calculateFitness(instructions, registers);
        var resolution = new ProgramResolution(new ArrayList<>(instructions), val);
        bestFitRegister.register(val,  resolution);
        return resolution;
    }

}
