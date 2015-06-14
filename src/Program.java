import statements.Instruction;
import statements.Register;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lveeckha on 14/06/2015.
 */
public class Program
{
    private List<Instruction> instructions = new ArrayList<Instruction>();
    private List<Register> registers = new ArrayList<Register>();

    public Program(List<Instruction> instructions, List<Register> registers) {
        this.instructions = instructions;
        this.registers = registers;
    }

    public List<Register> getRegisters() {
        return registers;
    }

    public List<Instruction> getInstructions() {
        return instructions;
    }
}
