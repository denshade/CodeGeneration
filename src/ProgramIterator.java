import statements.Instruction;
import statements.InstructionEnum;
import statements.InstructionFactory;
import statements.Register;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lveeckha on 31/05/2015.
 */
public class ProgramIterator
{
    public static int maximumInstructions = 12;
    public long counter = 0;
    public void iterate()
    {
        Register[] registers = new Register[4];
        for (int i = 0; i <  registers.length; i++){
            registers[i] = new Register("r"+i);
        }
        recurse(new ArrayList<Instruction>(), registers);
    }

    public void recurse(List<Instruction> instructions, Register[] registers)
    {
        if (instructions.size() >= maximumInstructions)
            return;
        for (InstructionEnum instruction : InstructionEnum.values())
        {
            for (int register1Index = 0; register1Index < registers.length; register1Index++)
            {
                Register register1 = registers[register1Index];
                if (instruction.isDualRegister())
                {
                    for (int register2Index = 0; register2Index < registers.length; register2Index++)
                    {
                        Register register2 = registers[register2Index];
                        Instruction actualInstruction = InstructionFactory.createInstruction(instruction, register1, register2);
                        instructions.add(actualInstruction);
                        eval(instructions);
                        recurse(instructions, registers);
                        instructions.remove(instructions.size() - 1);
                    }
                }
                else
                {
                        Instruction actualInstruction = InstructionFactory.createInstruction(instruction, register1);
                        instructions.add(actualInstruction);
                        eval(instructions);
                        recurse(instructions, registers);
                        instructions.remove(instructions.size() - 1);
                }

            }
        }
    }

    private void eval(List<Instruction> instructions) {
        counter++;
        if (counter % 10000000 == 0)
        {
            System.out.println(counter);
        }
    }
    public static void main(String[] args)
    {
        ProgramIterator iterator = new ProgramIterator();
        maximumInstructions = 4;
        iterator.iterate();
        System.out.println(iterator.counter);
    }
}
