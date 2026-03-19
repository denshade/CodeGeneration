package laboflieven;

import laboflieven.instructions.accinstructions.NoRegisterInstruction;
import laboflieven.instructions.regular.DualRegisterInstruction;
import laboflieven.registers.Register;
import laboflieven.instructions.regular.SingleRegisterInstruction;

import java.util.List;
import java.util.Map;

/**
 * Created by lveeckha on 14/06/2015.
 */
public class Program
{
    private final List<InstructionMark> instructions;
    private final List<Register> registers;
    /**
     * Binding rewrites instruction register references to point at the {@link #registers} instances
     * stored in this program. This is expensive and should only be done once per Program instance.
     *
     * Thread-safety: binding is guarded with double-checked locking so concurrent evaluations
     * don't race during the first bind.
     */
    private volatile boolean registersBound = false;


    public Program(List<InstructionMark> instructions, List<Register> registers) {
        this.instructions = instructions;
        this.registers = registers;
    }

    public List<Register> getRegisters() {
        return registers;
    }

    public List<InstructionMark> getInstructions() {
        return instructions;
    }

    public void initializeRegisters(Map<String, Double> registerValues)
    {
        // bind is expensive; perform it only once (per Program instance).
        // Double-checked locking ensures thread safety while keeping the fast path lock-free.
        if (!registersBound) {
            synchronized (this) {
                if (!registersBound) {
                    bindRegisters();
                    registersBound = true;
                }
            }
        }
        for (Register register : getRegisters())
        {
            /*if (!registerValues.containsKey(register.name)){
                throw new IllegalArgumentException("You have not specified the initial values for " + register.name);
            }*/
            register.value = registerValues.getOrDefault(register.name, 0.0);
        }
    }

    public void bindRegisters() {
        for (InstructionMark instr : instructions)
        {
            if (instr instanceof NoRegisterInstruction){
                continue;
            }
            for (Register reg : registers)
            {
                if (instr instanceof SingleRegisterInstruction)
                {

                    if (((SingleRegisterInstruction) instr).destination.name.equals(reg.name) && !((SingleRegisterInstruction) instr).destination.equals(reg)){
                        ((SingleRegisterInstruction) instr).destination = reg;
                    }
                }
                if (instr instanceof laboflieven.instructions.accinstructions.SingleRegisterInstruction)
                {

                    if (((laboflieven.instructions.accinstructions.SingleRegisterInstruction) instr).getRegister().name.equals(reg.name) && !((laboflieven.instructions.accinstructions.SingleRegisterInstruction) instr).getRegister().equals(reg)){
                        ((laboflieven.instructions.accinstructions.SingleRegisterInstruction) instr).setRegister(reg);
                        break; //Register found for this instruction has been found.
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
