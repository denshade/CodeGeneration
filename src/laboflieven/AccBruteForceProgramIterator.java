package laboflieven;

import laboflieven.accinstructions.*;
import laboflieven.statements.Register;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by lveeckha on 31/05/2015.
 */
public class AccBruteForceProgramIterator
{
    public int maximumInstructions;
    public long counter = 0;

    public List<List<AccRegisterInstruction>> positiveSolutions = new ArrayList<>();
    private AccProgramFitnessExaminer evaluator;
    private InstructionEnum[] instructionEnums;


    public AccBruteForceProgramIterator(AccProgramFitnessExaminer evaluator)
    {
        this.evaluator = evaluator;
        instructionEnums = InstructionEnum.values();
    }

    public AccBruteForceProgramIterator(AccProgramFitnessExaminer evaluator, InstructionEnum[] instructions)
    {
        this.evaluator = evaluator;
        instructionEnums = instructions;
    }

    public void iterate(final int nrOfRegisters, int maximumInstructions)
    {
        this.maximumInstructions = maximumInstructions;
        Register[] registers = new Register[nrOfRegisters];
        for (int i = 0; i <  registers.length; i++){
            registers[i] = new Register("r"+i);
        }
        recurse(new ArrayList<>(), registers);
    }

    private void recurse(List<AccRegisterInstruction> instructions, Register[] registers)
    {
        if (instructions.size() >= maximumInstructions)
            return;
        for (InstructionEnum instruction : instructionEnums)
        {
            //First instruction must be a push
            if (instructions.size() == 0 && !(instruction.equals(InstructionEnum.AccLeftPush) ||instruction.equals(InstructionEnum.AccRightPush)))
                continue;
            //Finish must be a push to a register.
            if (instructions.size() == maximumInstructions - 1 && !(instruction.equals(InstructionEnum.AccLeftPull) ||instruction.equals(InstructionEnum.AccRightPull)))
                continue;
            //Don't use pull from right/left before a push.
            if (instruction.equals(InstructionEnum.AccLeftPull))
            {
                boolean used = false;
                for ( AccRegisterInstruction instructionI: instructions)
                {
                    if (instructionI instanceof AccLeftPush) {
                        used = true;
                    }
                }
                if (!used)continue;
            }
            if (instruction.equals(InstructionEnum.AccRightPull))
            {
                boolean used = false;
                for ( AccRegisterInstruction instructionI: instructions)
                {
                    if (instructionI instanceof AccRightPush) {
                        used = true;
                    }
                }
                if (!used)continue;
            }
            if (instructions.size() == maximumInstructions - 1 && !(instruction.equals(InstructionEnum.AccLeftPull) ||instruction.equals(InstructionEnum.AccRightPull)))
                continue;
            if (instruction.isSingleRegister()) {
                for (Register register1 : registers) {
                        AccRegisterInstruction actualInstruction = InstructionFactory.createInstruction(instruction, register1);
                        instructions.add(actualInstruction);
                        eval(instructions, Arrays.asList(registers));
                        recurse(instructions, registers);
                        instructions.remove(instructions.size() - 1);
                    }
                } else {
                    AccRegisterInstruction actualInstruction = InstructionFactory.createInstruction(instruction);
                    instructions.add(actualInstruction);
                    eval(instructions, Arrays.asList(registers));
                    recurse(instructions, registers);
                    instructions.remove(instructions.size() - 1);
                }

        }
    }

    private void eval(List<AccRegisterInstruction> instructions, List<Register> registers) {
        if (evaluator.isFit(instructions, registers)){
            System.out.println("Found a program: " + instructions);
            positiveSolutions.add(new ArrayList<>(instructions));
            evaluator.calculateFitness(instructions, registers);
            System.exit(1);
        } else {
            double err = evaluator.calculateFitness(instructions, registers);
            if (err < 100 && counter++ % 100000 == 0)
                System.out.println("Current fitness " + err + " " + instructions);
        }
    }

}
