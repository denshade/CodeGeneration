package laboflieven.instructions.logic;


import laboflieven.InstructionMark;

/**
 * Created by lveeckha on 31/05/2015.
 */
public abstract class Instruction implements InstructionMark
{
    public Instruction()
    {}
    public abstract Integer execute(int current);

    public abstract String toString();

    @Override
    public boolean equals(Object obj) {
        return toString().equals(obj.toString());
    }
}
