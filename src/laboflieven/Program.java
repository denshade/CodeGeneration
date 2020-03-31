package laboflieven;

import laboflieven.statements.DualRegisterInstruction;
import laboflieven.statements.Instruction;
import laboflieven.statements.Register;
import laboflieven.statements.SingleRegisterInstruction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    /**
     *
     * @param registerValues
     */
    public void initializeRegisters(Map<String, Double> registerValues)
    {
        bindRegisters();
        for (Register register : getRegisters())
        {
            if (!registerValues.containsKey(register.name)){
                throw new IllegalArgumentException("You have not specified the initial values for " + register.name);
            }
            register.value = registerValues.get(register.name);
        }
    }

    public void bindRegisters() {
        for (Instruction instr : instructions)
        {
            for (Register reg : registers)
            {
                if (instr instanceof SingleRegisterInstruction)
                {

                    if (((SingleRegisterInstruction) instr).destination.name.equals(reg.name) && !((SingleRegisterInstruction) instr).destination.equals(reg)){
                        ((SingleRegisterInstruction) instr).destination = reg;
                    }
                }
                if (instr instanceof DualRegisterInstruction)
                {
                    DualRegisterInstruction instruction = (DualRegisterInstruction) instr;
                    if ( instruction.destination.name.equals(reg.name) && !(instruction.destination.equals(reg))){
                        instruction.destination = reg;
                    }
                    if ( instruction.source.name.equals(reg.name) && !(instruction.source.equals(reg))){
                        instruction.source = reg;
                    }
                }

            }
        }
    }

    public String toString()
    {
        return instructions.toString();
    }
}
