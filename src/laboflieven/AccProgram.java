package laboflieven;

import laboflieven.accinstructions.*;
import laboflieven.statements.Instruction;
import laboflieven.statements.Register;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lveeckha on 14/06/2015.
 */
public class AccProgram
{
    private List<AccRegisterInstruction> instructions = new ArrayList<>();
    private List<Register> registers = new ArrayList<Register>();

    public AccProgram(List<AccRegisterInstruction> instructions, List<Register> registers) {
        this.instructions = instructions;
        this.registers = registers;
    }

    public List<Register> getRegisters() {
        return registers;
    }

    public List<AccRegisterInstruction> getInstructions() {
        return instructions;
    }

    /**
     *
     * @param registerValues
     */
    public void initializeRegisters(Map<String, Double> registerValues)
    {
        for (Register register : getRegisters())
        {
            if (!registerValues.containsKey(register.name)){
                throw new IllegalArgumentException("You have not specified the initial values for " + register.name);
            }
            register.value = registerValues.get(register.name);
        }
    }

    public boolean isUseless(AccRegisterInstruction instruction, int maximumInstructions)
    {
        //First instruction must be a push
        if (instructions.size() == 0 && !(instruction instanceof AccLeftPush ||instruction instanceof AccRightPush))
            return true;
        //Finish must be a push to a register.
        if (instructions.size() == maximumInstructions - 1 && !(instruction instanceof AccLeftPull ||instruction instanceof  AccRightPull))
            return true;
        //Don't use pull from right/left before a push.
        if (instruction instanceof AccLeftPull)
        {
            boolean used = false;
            for ( AccRegisterInstruction instructionI: instructions)
            {
                if (instructionI instanceof AccLeftPush) {
                    used = true;
                }
            }
            if (!used)return true;
        }
        if (instruction instanceof AccRightPull)
        {
            boolean used = false;
            for ( AccRegisterInstruction instructionI: instructions)
            {
                if (instructionI instanceof AccRightPush) {
                    used = true;
                }
            }
            if (!used)return true;
        }
        return false;
    }

    public String toString()
    {
        return instructions.toString();
    }
}
