package laboflieven;

import laboflieven.statements.Instruction;
import laboflieven.statements.InstructionEnum;
import laboflieven.statements.InstructionFactory;
import laboflieven.statements.Register;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Created by Lieven on 11-6-2016.
 */
public class PriorityProgramIterator
{

    public static final int MAXBADPROGRAM = 300;
    private final ProgramFitnessExaminer evaluator;
    private final InstructionEnum[] enums;
    PriorityQueue<ComparableProgram> priorityQueue = new PriorityQueue<>();
    private Register[] registers;

    public PriorityProgramIterator(ProgramFitnessExaminer evaluator, InstructionEnum[] enums) {
        this.evaluator = evaluator;
        this.enums = enums;
    }

    public void iterate(final int nrOfRegisters, int maximumInstructions)
    {

        registers = new Register[nrOfRegisters];
        for (int i = 0; i < registers.length; i++) {
            registers[i] = new Register("r" + i);
        }
        List<Register> registerList = Arrays.asList(registers);
        fillFirstPrograms(registerList);
        while (priorityQueue.size() > 0)
        {
            ComparableProgram currentProgram = priorityQueue.remove();
            if (priorityQueue.size()% 1000 == 0) System.out.println(priorityQueue.size() + ":" + currentProgram);
            if (currentProgram.getProgram().getInstructions().size() < maximumInstructions)
            {
                createNewProgramsFrom(currentProgram);
            }
        }
    }

    private void createNewProgramsFrom(ComparableProgram currentProgram) {
        List<Register> registerList = Arrays.asList(registers);
        List<InstructionMark> instructionsSoFar = currentProgram.getProgram().getInstructions();

        for (InstructionEnum instruction : enums)
        {
            if (instruction.isDualRegister()) {
                for (Register register1 : registers) {

                    for (Register register2 : registers) {
                        if (instruction == InstructionEnum.Move && register1.name.equals(register2.name)) {
                            continue;
                        }
                        Instruction actualInstruction = InstructionFactory.createInstruction(instruction, register1, register2);
                        List<InstructionMark> newInstructions = new ArrayList<>(instructionsSoFar);
                        newInstructions.add(actualInstruction);
                        Program program = new Program(newInstructions, registerList);

                        ComparableProgram e = new ComparableProgram(program, evaluator);
                        addifImportant(e);
                    }
                }
            }
            else {
                for (Register register1 : registers) {
                    Instruction actualInstruction = InstructionFactory.createInstruction(instruction, register1);
                    List<InstructionMark> newInstructions = new ArrayList<>(instructionsSoFar);
                    newInstructions.add(actualInstruction);
                    Program program = new Program(newInstructions, registerList);
                    ComparableProgram e = new ComparableProgram(program, evaluator);
                    addifImportant(e);
                }
            }
        }
    }

    private void addifImportant(ComparableProgram e) {
        if (e.getScore() == 0)
        {
            System.out.println("Found a solution " + e.getProgram());
            throw new StoppedByUserException();
        }
        if (!(Double.isNaN(e.getScore()) || Double.isInfinite(e.getScore()) || e.getScore() > MAXBADPROGRAM))
        {
            priorityQueue.add(e);
        }
    }

    private void fillFirstPrograms(List<Register> registerList) {
        for (InstructionEnum instruction : enums)
        {
            if (instruction.isDualRegister()) {
                for (Register register1 : registers) {

                    for (Register register2 : registers) {
                        if (instruction == InstructionEnum.Move && register1.name.equals(register2.name)) {
                            continue;
                        }
                        Instruction actualInstruction = InstructionFactory.createInstruction(instruction, register1, register2);
                        Program program = new Program(Arrays.asList(actualInstruction), registerList);
                        addifImportant(new ComparableProgram(program, evaluator));
                    }
                }
            }
            else {
                for (Register register1 : registers) {
                    Instruction actualInstruction = InstructionFactory.createInstruction(instruction, register1);
                    Program program = new Program(Arrays.asList(actualInstruction), registerList);
                    addifImportant(new ComparableProgram(program, evaluator));
                }
            }
        }
    }


}
