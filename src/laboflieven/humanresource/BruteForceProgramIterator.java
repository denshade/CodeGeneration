package laboflieven.humanresource;

import laboflieven.humanresource.instructions.Inbox;
import laboflieven.humanresource.instructions.Jump;
import laboflieven.humanresource.instructions.JumpIfZero;
import laboflieven.humanresource.instructions.Outbox;
import laboflieven.humanresource.model.HumanInstruction;
import laboflieven.humanresource.model.HumanInstructionEnum;
import laboflieven.humanresource.model.HumanInstructionFactory;
import laboflieven.humanresource.model.HumanRegister;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by lveeckha on 31/05/2015.
 */
public class BruteForceProgramIterator
{
    public int maximumInstructions;
    public long counter = 0;
    public long secondsSinceEpochStart = System.currentTimeMillis() / 1000;

    public List<List<HumanInstruction>> positiveSolutions = new ArrayList<>();
    private HumanProgramFitnessExaminer evaluator;
    private HumanInstructionEnum[] instructionEnums;

    public BruteForceProgramIterator(HumanProgramFitnessExaminer evaluator, HumanInstructionEnum[] instructions)
    {
        this.evaluator = evaluator;
        instructionEnums = instructions;
    }

    public void iterate(final int nrOfRegisters, int maximumInstructions)
    {
        this.maximumInstructions = maximumInstructions;
        HumanRegister[] registers = new HumanRegister[nrOfRegisters];
        for (int i = 0; i <  registers.length; i++){
            registers[i] = new HumanRegister("r"+i);
        }
        recurse(new ArrayList<>(), registers);
    }

    private void recurse(List<HumanInstruction> instructions, HumanRegister[] registers)
    {
        if (instructions.size() >= maximumInstructions)
            return;
        for (HumanInstructionEnum instruction : instructionEnums)
        {
            if (isUseless(instructions, instruction))
            {
                continue;
            }
            if (instruction.isSingleRegister()) {
                for (HumanRegister register1 : registers) {
                    HumanInstruction actualInstruction = HumanInstructionFactory.createInstruction(instruction, register1);
                    instructions.add(actualInstruction);
                    if (isValid(instructions, Arrays.asList(registers)))
                    {
                        if (instructions.size() == maximumInstructions  && instructionsHasInboxOutbox(instructions))
                            eval(instructions, Arrays.asList(registers));
                        recurse(instructions, registers);
                    }
                    instructions.remove(instructions.size() - 1);
                }
            } else if (instruction.isLoop())
            {
                for (int jumpToInstruction = 0; jumpToInstruction < maximumInstructions; jumpToInstruction++) {
                    if (jumpToInstruction == instructions.size() - 1)//jump to self.
                        continue;
                    HumanInstruction actualInstruction = HumanInstructionFactory.createLoopInstruction(instruction, jumpToInstruction);
                    instructions.add(actualInstruction);
                    if (isValid(instructions, Arrays.asList(registers)))
                    {
                        if (instructions.size() == maximumInstructions  && instructionsHasInboxOutbox(instructions))
                            eval(instructions, Arrays.asList(registers));
                        recurse(instructions, registers);
                    }
                    instructions.remove(instructions.size() - 1);
                }

            } else {
                HumanInstruction actualInstruction = HumanInstructionFactory.createInstruction(instruction);
                instructions.add(actualInstruction);
                if (isValid(instructions, Arrays.asList(registers)))
                {
                    if (instructions.size() == maximumInstructions && instructionsHasInboxOutbox(instructions))
                        eval(instructions, Arrays.asList(registers));
                    recurse(instructions, registers);
                }
                instructions.remove(instructions.size() - 1);
                }
        }
    }

    private boolean isUseless(List<HumanInstruction> instructions, HumanInstructionEnum instruction) {
        return isInboxAfterInbox(instructions, instruction)
                || isJumpAfterJump(instructions, instruction)
                || isInboxJumpTo0(instructions, instruction)
                ;
    }


    private boolean instructionsHasInboxOutbox(List<HumanInstruction> instructions) {
        boolean hasInbox = false;
        boolean hasOutbox = false;

        for(HumanInstruction inst : instructions)
        {
            if (inst instanceof Inbox)
                hasInbox = true;
            if (inst instanceof Outbox)
                hasOutbox = true;
        }
        return hasInbox && hasOutbox;
    }

    private boolean isInboxJumpTo0(List<HumanInstruction> instructions, HumanInstructionEnum instruction) {
        if (instructions.size() > 2 && instructions.get(0) instanceof Inbox && instructions.get(1) instanceof Jump) return true;
        return false;
    }

    private boolean isJumpAfterJump(List<HumanInstruction> instructions, HumanInstructionEnum instruction) {
        return (instruction == HumanInstructionEnum.LOOP && instructions.size() > 0 && instructions.get(instructions.size() - 1) instanceof Jump) ||
                (instruction == HumanInstructionEnum.JumpIfZero && instructions.size() > 0 && instructions.get(instructions.size() - 1) instanceof JumpIfZero);
    }

    private boolean isInboxAfterInbox(List<HumanInstruction> instructions, HumanInstructionEnum instruction) {
        return instruction == HumanInstructionEnum.INBOX && instructions.size() > 0 && instructions.get(instructions.size() - 1) instanceof Inbox;
    }

    private void eval(List<HumanInstruction> instructions, List<HumanRegister> registers) {
        counter++;
        if (evaluator.isFit(instructions, registers)){
            System.out.println("Found a program: " + instructions);
            positiveSolutions.add(new ArrayList<>(instructions));
        } else {
            if (counter % 10000000 == 0)
            {
                long secondsSinceEpochNow = System.currentTimeMillis() + 1000;
                long secondsPassed = secondsSinceEpochNow - secondsSinceEpochStart;
                System.out.println((secondsPassed / counter));
            }
        }
    }

    private boolean isValid(List<HumanInstruction> instructions, List<HumanRegister> registers) {
        return evaluator.isValid(instructions, registers);
    }

}
