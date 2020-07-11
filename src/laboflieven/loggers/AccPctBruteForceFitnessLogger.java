package laboflieven.loggers;

import laboflieven.InstructionMark;
import laboflieven.accinstructions.InstructionEnum;
import laboflieven.accinstructions.InstructionFactory;
import laboflieven.common.AccInstructionSet;
import laboflieven.statements.InstructionFactoryInterface;
import laboflieven.statements.Register;

import java.util.ArrayList;
import java.util.List;

public class AccPctBruteForceFitnessLogger implements FitnessLogger
{
    private final InstructionEnum[] availableInstructions;
    private final int refreshRateInMs;
    private final int nrRegisters;
    private List<InstructionMark> exampleInstructions;
    private InstructionFactoryInterface instructionFactory = new InstructionFactory();

    private long lastPrint;

    public AccPctBruteForceFitnessLogger(InstructionEnum[] instructions, int refreshRateInMs, int nrRegisters)
    {
        this.availableInstructions = instructions;
        this.refreshRateInMs = refreshRateInMs;
        this.nrRegisters = nrRegisters;
        exampleInstructions = getExampleInstructions(nrRegisters);
    }
    public void addFitness(List<InstructionMark> instructions, int nrInstruction, int nrRegisters, double error)
    {
        //if (instructions.size() != nrInstruction) return;
        if (System.currentTimeMillis() - lastPrint  > refreshRateInMs) {
            lastPrint = System.currentTimeMillis();
            System.out.println(calculatePct(instructions, nrInstruction, nrRegisters)*100 + "% ");
        }
    }
    public double calculatePct(List<InstructionMark> instructions, int nrInstruction, int nrRegisters)
    {
        //get index of first instruction.
        double progress = 0;
        for (int instructionPointer = 0; instructionPointer  < instructions.size(); instructionPointer++)
        {
            InstructionMark mark = instructions.get(instructionPointer);
            double instructionIndex = getInstructionIndex(mark, nrRegisters);
            int length = getLength(nrRegisters);
            progress += instructionIndex / Math.pow(length, instructionPointer + 1);
        }
        return progress;
    }

    private int getLength(int nrRegisters)
    {
        return exampleInstructions.size();

    }

    private int getInstructionIndex(InstructionMark mark, int nrRegisters) {
        List<InstructionMark> instructions = exampleInstructions;
        for (int i = 0; i < instructions.size(); i++)
        {
            if (mark.toString().equals(instructions.get(i).toString()))
            {
                return i;
            }
        }
        return 0;
    }

    private List<InstructionMark> getExampleInstructions(int nrRegisters) {
        List<InstructionMark> instructions = new ArrayList<>();
        for (InstructionEnum instructionEnum : this.availableInstructions) {
            if (instructionEnum.isSingleRegister()) {
                for (int register = 0; register < nrRegisters; register++) {
                    instructions.add(instructionFactory.createInstruction(new AccInstructionSet(instructionEnum), new Register("r" + register)));
                }
            } else {
                instructions.add(instructionFactory.createInstruction(new AccInstructionSet(instructionEnum)));
            }
        }
        return instructions;
    }
}
