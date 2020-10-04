package laboflieven.programiterators;

import laboflieven.*;
import laboflieven.accinstructions.AccInstructionOpcodeEnum;
import laboflieven.common.*;
import laboflieven.examiners.ProgramFitnessExaminerInterface;
import laboflieven.statements.InstructionFactory;
import laboflieven.statements.InstructionFactoryInterface;
import laboflieven.statements.Register;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

/**
 * Created by Lieven on 11-6-2016.
 */
public class AccPriorityProgramIterator  implements ProgramIterator
{

    private ProgramFitnessExaminerInterface evaluator;
    private AccInstructionOpcodeEnum[] enums;
    PriorityQueue<ProgramResolution> priorityQueue = new PriorityQueue<>();
    private Register[] registers;
    private InstructionFactoryInterface instructionFactory;
    private BestFitRegister<ProgramResolution> bestFitRegister = new BestFitRegister<>();


    public ProgramResolution iterate(Configuration configuration)
    {
        this.evaluator = configuration.getFitnessExaminer();
        this.enums = configuration.getAccOperations();
        this.instructionFactory = configuration.getInstructionFactory();
        List<Register> registerList = Register.createRegisters(configuration.getNumberOfRegisters(2), "R");
        this.registers = registerList.toArray(new Register[0]);
        int CUT_POPULATION_AT_MAX = configuration.getCutPopulationAtMax(150000);
        int CUT_POPULATION_TO = configuration.getCutPopulationTo(100000);
        boolean addRandom = configuration.getRandomAdded(true);
        addLevel(registerList, new ArrayList<>());
        while (priorityQueue.size() > 0)
        {
            ProgramResolution res = priorityQueue.poll();
            List<InstructionMark> instructions = res.instructions;
            if (instructions.size() < configuration.getMaxNrInstructions(10)) {
                addLevel(registerList, instructions);
            } else {
                if (priorityQueue.size() > CUT_POPULATION_AT_MAX)
                {
                    System.out.println("Cutting population");
                    priorityQueue = PriorityQueueAlgos.cutPopulation(CUT_POPULATION_TO, priorityQueue);
                }
            }
            if (addRandom)
            {
                createRandom(registerList, res);
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

    private void addLevel(List<Register> registerList, List<InstructionMark> instructions) {
        for (AccInstructionOpcodeEnum opcode : enums) {
            AccInstructionOpcode opcodeR = new AccInstructionOpcode(opcode);
            if (opcodeR.getNrRegisters() == 0) {
                List<InstructionMark> marks = new ArrayList<>(instructions);
                marks.add(instructionFactory.createInstruction(opcodeR));
                priorityQueue.add(eval(marks, registerList));
            } else {
                for (Register r : registers) {
                    List<InstructionMark> marks = new ArrayList<>(instructions);
                    marks.add(instructionFactory.createInstruction(opcodeR, r));
                    priorityQueue.add(eval(marks, registerList));
                }
            }
        }
    }

    private ProgramResolution eval(List<InstructionMark> instructions, List<Register> registers) {
        double val =  evaluator.calculateFitness(instructions, registers);
        bestFitRegister.register(val,  new ProgramResolution(new ArrayList<>(instructions), val));
        return new ProgramResolution(new ArrayList<>(instructions), val);
    }

}
