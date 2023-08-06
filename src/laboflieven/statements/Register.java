package laboflieven.statements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lveeckha on 31/05/2015.
 */
public class Register
{
    public String name;

    public Register(String name)
    {
        this.name = name;
    }
    public double value;
    public String toString()
    {
        return name;
    }

    public static List<Register> createRegisters(int count, String prefix)
    {
        List<Register> registers = new ArrayList<Register>();
        for (int k = 1; k <= count; k++)
        {
            registers.add(new Register(prefix + k));
        }
        return registers;
    }

    public static List<Register> createRegisters(int count)
    {
        List<Register> registers = new ArrayList<Register>();
        for (int k = 1; k <= count; k++)
        {
            registers.add(new Register("R" + k));
        }
        return registers;
    }

}
