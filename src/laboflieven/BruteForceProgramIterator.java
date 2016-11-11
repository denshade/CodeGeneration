package laboflieven;

import laboflieven.statements.Instruction;
import laboflieven.statements.InstructionEnum;
import laboflieven.statements.InstructionFactory;
import laboflieven.statements.Register;

import java.util.*;

/**
 * Created by lveeckha on 31/05/2015.
 */
public class BruteForceProgramIterator
{
    public int maximumInstructions;
    public long counter = 0;

    public List<List<Instruction>> positiveSolutions = new ArrayList<>();
    private ProgramEvaluator evaluator;
    private InstructionEnum[] instructionEnums;


    public BruteForceProgramIterator(ProgramEvaluator evaluator)
    {
        this.evaluator = evaluator;
        instructionEnums = InstructionEnum.values();
    }

    public BruteForceProgramIterator(ProgramEvaluator evaluator, InstructionEnum[] instructions)
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

    private void recurse(List<Instruction> instructions, Register[] registers)
    {
        if (instructions.size() >= maximumInstructions)
            return;
        for (InstructionEnum instruction : instructionEnums)
        {
            for (Register register1 : registers) {
                if (instruction.isDualRegister()) {
                    for (Register register2 : registers) {
                        if (instruction == InstructionEnum.Move && register1.name.equals(register2.name))
                        {
                            continue;
                        }
                        Instruction actualInstruction = InstructionFactory.createInstruction(instruction, register1, register2);
                        instructions.add(actualInstruction);
                        eval(instructions, Arrays.asList(registers));
                        recurse(instructions, registers);
                        instructions.remove(instructions.size() - 1);
                    }
                } else {
                    Instruction actualInstruction = InstructionFactory.createInstruction(instruction, register1);
                    instructions.add(actualInstruction);
                    eval(instructions, Arrays.asList(registers));
                    recurse(instructions, registers);
                    instructions.remove(instructions.size() - 1);
                }

            }
        }
    }

    private void eval(List<Instruction> instructions, List<Register> registers) {
        counter++;
        if (evaluator.evaluate(instructions, registers)){
            System.out.println("Found a program: " + instructions);
            positiveSolutions.add(new ArrayList<>(instructions));
        }
    }

}
