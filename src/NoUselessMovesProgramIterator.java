import statements.Instruction;
import statements.InstructionEnum;
import statements.InstructionFactory;
import statements.Register;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lveeckha on 31/05/2015.
 */
public class NoUselessMovesProgramIterator
{
    public static int maximumInstructions = 12;
    public long counter = 0;
    private Map<String, Double> initialParameters;


    public NoUselessMovesProgramIterator(Map<String, Double> initialParameters)
    {
        this.initialParameters = initialParameters;
    }

    public List<List<Instruction>> positiveSolutions = new ArrayList<List<Instruction>>();
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
                        if (instruction == InstructionEnum.Move && register1Index == register2Index)
                        {
                            continue;
                        }
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
        StatementRunner runner = new StatementRunner();

        List<Register> registers = Register.create4Registers();
        Program program = new Program(instructions, Register.create4Registers());

        runner.execute(program, initialParameters);
        if (registers.get(3).value == 6){
            positiveSolutions.add(new ArrayList<Instruction>(instructions));
        }
        if (counter % 10000000 == 0)
        {
            System.out.println(counter);
        }
    }
    public static void main(String[] args)
    {
        Map<String, Double> startParameters = new HashMap<String, Double>(4);
        startParameters.put("r1", 2.0);
        startParameters.put("r2", -8.0);
        startParameters.put("r3", -24.0);
        startParameters.put("r4", 0.0);

        NoUselessMovesProgramIterator iterator = new NoUselessMovesProgramIterator(startParameters);
        maximumInstructions = 2;
        iterator.iterate();
        System.out.println(iterator.counter);
        System.out.println(iterator.positiveSolutions.size());
    }
}
