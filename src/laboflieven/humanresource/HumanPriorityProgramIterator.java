package laboflieven.humanresource;

import laboflieven.InstructionMark;
import laboflieven.Program;
import laboflieven.ProgramResolution;
import laboflieven.accinstructions.AccInstructionOpcodeEnum;
import laboflieven.common.AccInstructionOpcode;
import laboflieven.common.BestFitRegister;
import laboflieven.common.Configuration;
import laboflieven.common.PriorityQueueAlgos;
import laboflieven.examiners.ProgramFitnessExaminerInterface;
import laboflieven.humanresource.heuristics.AlwaysRecurseHeuristic;
import laboflieven.humanresource.heuristics.HumanRecursionHeuristic;
import laboflieven.humanresource.model.HumanInstruction;
import laboflieven.humanresource.model.HumanInstructionEnum;
import laboflieven.humanresource.model.HumanInstructionFactory;
import laboflieven.humanresource.model.HumanRegister;
import laboflieven.programiterators.ProgramIterator;
import laboflieven.recursionheuristics.RecursionHeuristic;
import laboflieven.statements.InstructionFactoryInterface;
import laboflieven.statements.Register;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Created by Lieven on 11-6-2016.
 */
public class HumanPriorityProgramIterator
{

    private HumanInstructionEnum[] enums;
    private HumanProgramFitnessExaminer examiner;
    PriorityQueue<HumanProgramResolution> priorityQueue = new PriorityQueue<>();
    private HumanRegister[] registers;
    private HumanRecursionHeuristic heuristic = new AlwaysRecurseHeuristic();
    private HumanInstructionFactory instructionFactory;
    private BestFitRegister<HumanProgramResolution> bestFitRegister = new BestFitRegister<>();


    public ProgramResolution iterate(Configuration configuration, HumanInstructionEnum[] enums, HumanProgramFitnessExaminer examiner)
    {
        this.enums = enums;
        this.examiner = examiner;
        List<HumanRegister> registerList = HumanRegister.createRegisters(configuration.getNumberOfRegisters(2), "r");
        this.registers = registerList.toArray(new HumanRegister[0]);
        int CUT_POPULATION_AT_MAX = configuration.getCutPopulationAtMax(150000);
        int CUT_POPULATION_TO = configuration.getCutPopulationTo(100000);
        boolean addRandom = configuration.getRandomAdded(true);
        addLevel(registerList, new ArrayList<>(), configuration);
        while (priorityQueue.size() > 0)
        {
            HumanProgramResolution res = priorityQueue.poll();
            List<HumanInstruction> instructions = res.instructions;
            if (instructions.size() < configuration.getMaxNrInstructions(10)) {
                //Program prog = new Program(instructions, registerList);
                HumanProgramResolution score = eval(instructions, registerList);
                if (score.weight < 1000000) {
                    if (heuristic.shouldRecurse(instructions)) {
                        addLevel(registerList, instructions, configuration);
                    } else {
                        System.out.println("Skipped level of " + instructions);
                    }
                }
            } else {
                if (priorityQueue.size() > CUT_POPULATION_AT_MAX)
                {
                    System.out.println("Cutting population");
                    priorityQueue = PriorityQueueAlgos.cutPopulation(CUT_POPULATION_TO, priorityQueue);
                }
            }
/*            if (addRandom)
            {
                createRandom(registerList, res);
            }*/
        }
        return null;
    }

    /*private void createRandom(List<Register> registerList, ProgramResolution res) {
        List<InstructionMark> randomProgram = new ArrayList<>();
        for (int k = 0; k < res.instructions.size(); k++) {
            randomProgram.add(instructionFactory.generateRandomInstruction(registerList));
        }
        ProgramResolution eval = eval(randomProgram, registerList);
        if (eval.weight / 2 < bestFitRegister.getBestScore())
        {
            priorityQueue.add(eval);
        }
    }*/

    private void addLevel(List<HumanRegister> registerList, List<HumanInstruction> instructions, Configuration config) {
        for (HumanInstructionEnum opcode : enums) {
            if (!opcode.isSingleRegister() && !opcode.isLoop()) {
                List<HumanInstruction> marks = new ArrayList<>(instructions);
                marks.add(instructionFactory.createInstruction(opcode));
                priorityQueue.add(eval(marks, registerList));
            } else if (opcode.isSingleRegister()) {
                for (HumanRegister r : registers) {
                    List<HumanInstruction> marks = new ArrayList<>(instructions);
                    marks.add(instructionFactory.createInstruction(opcode, r));
                    priorityQueue.add(eval(marks, registerList));
                }
            } else if (opcode.isLoop()) {
                for (int k = 0; k < config.getMaxNrInstructions(10); k++) {
                    List<HumanInstruction> marks = new ArrayList<>(instructions);
                    marks.add(HumanInstructionFactory.createLoopInstruction(opcode, k));
                    priorityQueue.add(eval(marks, registerList));
                }
            }
        }
    }

    private HumanProgramResolution eval(List<HumanInstruction> instructions, List<HumanRegister> registers) {
        double val =  examiner.calculateFitness(instructions, registers);
        var res = new HumanProgramResolution();
        res.instructions = new ArrayList<>(instructions);
        res.weight = val;
        bestFitRegister.register(val, res);
        return res;
    }

}
