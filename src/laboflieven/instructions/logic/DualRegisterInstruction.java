package laboflieven.instructions.logic;

import laboflieven.instructions.regular.Instruction;
import laboflieven.registers.Register;
import laboflieven.registers.TemplateRegister;

/**
 * Created by lveeckha on 31/05/2015.
 */
public abstract class DualRegisterInstruction extends Instruction {

    public TemplateRegister<Boolean> source;
    public TemplateRegister<Boolean> destination;

    public DualRegisterInstruction(TemplateRegister<Boolean> source, TemplateRegister<Boolean> destination)
    {
        this.source = source;
        this.destination = destination;
    }

    abstract public Integer execute(int current);

    public String toString()
    {
        return this.getClass().getSimpleName() + " " + source + " -> " + destination ;
    }

}
