package laboflieven;

import laboflieven.instructions.regular.InstructionFactory;
import laboflieven.instructions.regular.InstructionFactoryInterface;
import laboflieven.instructions.regular.RegularInstructionOpcodeEnum;
import laboflieven.registers.Register;

import java.util.*;

/**
 * Created by lveeckha on 31/05/2015.
 */
public class ReverseProgramIterator {
    private int maximumInstructions = 12;
    public long counter = 0;
    private final int programCount;
    private final InstructionFactoryInterface instructionFactory = new InstructionFactory();


    public ReverseProgramIterator(int programCount)
    {
        this.programCount = programCount;
    }

    public Program iterate(int nrRegisters, int maximumInstructions)
    {
        this.maximumInstructions = maximumInstructions;
        Register[] registers = new Register[nrRegisters];
        for (int i = 0; i <  registers.length; i++){
            registers[i] = new Register("r"+i);
        }
        return recurse(new ArrayList<>(), registers);
    }

    public Program recurse(List<InstructionMark> instructions, Register[] registers)
    {
        if (instructions.size() >= maximumInstructions)
            return null;
        List<Register> registerList = new ArrayList<>();
        for(Register r : registers)
        {
            registerList.add(r);
        }
        for (RegularInstructionOpcodeEnum instruction : RegularInstructionOpcodeEnum.values())
        {
            for (int register1Index = 0; register1Index < registers.length; register1Index++)
            {
                Register register1 = registers[register1Index];
                if (instruction.isDualRegister())
                {
                    for (int register2Index = 0; register2Index < registers.length; register2Index++)
                    {
                        if (instruction == RegularInstructionOpcodeEnum.Move && register1Index == register2Index)
                        {
                            continue;
                        }
                        Register register2 = registers[register2Index];
                        InstructionMark actualInstruction = instructionFactory.createInstruction(new laboflieven.common.RegularInstructionOpcode(instruction), register1, register2);
                        instructions.add(actualInstruction);
                        Program sol = eval(instructions, registerList);
                        if (sol != null)
                        {
                            return sol;
                        }
                        sol = recurse(instructions, registers);
                        if (sol != null)
                        {
                            return sol;
                        }
                        instructions.remove(instructions.size() - 1);
                    }
                }
                else
                {
                    InstructionMark actualInstruction = instructionFactory.createInstruction(new laboflieven.common.RegularInstructionOpcode(instruction), register1);
                    instructions.add(actualInstruction);
                    Program sol = eval(instructions, registerList);
                    if (sol != null)
                    {
                        return sol;
                    }
                    sol = recurse(instructions, registers);
                    if (sol != null)
                    {
                        return sol;
                    }
                    instructions.remove(instructions.size() - 1);
                }

            }
        }
        return null;
    }

    private Program eval(List<InstructionMark> instructions, List<Register> registers) {
        counter++;
        //StatementRunner runner = new StatementRunner();

        Program program = new Program(instructions, registers);

        //runner.execute(program, initialParameters);
        if (counter >= programCount) {
            return program;
        }
        return null;

    }
}
