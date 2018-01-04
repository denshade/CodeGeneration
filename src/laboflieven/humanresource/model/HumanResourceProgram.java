package laboflieven.humanresource.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lieven on 2-1-2018.
 */
public class HumanResourceProgram
{
    private List<HumanInstruction> instructions = new ArrayList<>();
    private List<HumanRegister> registers = new ArrayList<>();

    public HumanResourceProgram(List<HumanInstruction> instructions, List<HumanRegister> registers) {
        this.instructions = instructions;
        this.registers = registers;
    }

    public List<HumanRegister> getRegisters() {
        return registers;
    }

    public List<HumanInstruction> getInstructions() {
        return instructions;
    }

    public String toString()
    {
        String s = "";
        for (int i = 0; i < instructions.size(); i++) {
            s += "[i:"+instructions.get(i)+"]" ;
        }
        return s;
    }
}
