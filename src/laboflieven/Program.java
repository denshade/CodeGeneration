package laboflieven;

import laboflieven.accinstructions.NoRegisterInstruction;
import laboflieven.statements.DualRegisterInstruction;
import laboflieven.statements.Register;
import laboflieven.statements.SingleRegisterInstruction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lveeckha on 14/06/2015.
 */
public class Program
{
    private List<InstructionMark> instructions;
    private List<Register> registers;
    private Map<String, Register> registerMap;


    public Program(List<InstructionMark> instructions, List<Register> registers) {
        registerMap = new HashMap<>(registers.size());
        this.instructions = instructions;
        this.registers = registers;
        for(Register register : registers ) {
            registerMap.put(register.name, register);
        }
    }

    public List<Register> getRegisters() {
        return registers;
    }

    public List<InstructionMark> getInstructions() {
        return instructions;
    }

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
        for (InstructionMark instr : instructions)
        {
            if (instr instanceof NoRegisterInstruction){
                continue;
            }
            for (Register reg : registers)
            {
                if (instr instanceof laboflieven.statements.SingleRegisterInstruction)
                {

                    if (((SingleRegisterInstruction) instr).destination.name.equals(reg.name) && !((SingleRegisterInstruction) instr).destination.equals(reg)){
                        ((SingleRegisterInstruction) instr).destination = reg;
                    }
                }
                if (instr instanceof laboflieven.accinstructions.SingleRegisterInstruction)
                {

                    if (((laboflieven.accinstructions.SingleRegisterInstruction) instr).getRegister().name.equals(reg.name) && !((laboflieven.accinstructions.SingleRegisterInstruction) instr).getRegister().equals(reg)){
                        ((laboflieven.accinstructions.SingleRegisterInstruction) instr).setRegister(reg);
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
