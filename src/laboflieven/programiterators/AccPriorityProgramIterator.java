package laboflieven.programiterators;

import laboflieven.*;
import laboflieven.accinstructions.AccInstructionOpcodeEnum;
import laboflieven.common.AccInstructionOpcode;
import laboflieven.common.BestFitRegister;
import laboflieven.common.Configuration;
import laboflieven.common.InstructionOpcode;
import laboflieven.examiners.ProgramFitnessExaminerInterface;
import laboflieven.statements.InstructionFactory;
import laboflieven.statements.InstructionFactoryInterface;
import laboflieven.statements.Register;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

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
        addLevel(registerList, new ArrayList<>());
        while (priorityQueue.size() > 0)
        {
            ProgramResolution res = priorityQueue.poll();
            List<InstructionMark> instructions = res.instructions;
            if (instructions.size() < configuration.getMaxNrInstructions(10)) {
                addLevel(registerList, instructions);
            } else {
                if (priorityQueue.size() % 1000 == 0)
                    System.out.println(priorityQueue.size());
            }
        }
        return null;
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
