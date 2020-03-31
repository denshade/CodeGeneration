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

    public static List<Register> create4Registers()
    {
        Register r1 = new Register("r1");//a
        Register r2 = new Register("r2");//b
        Register r3 = new Register("r3");//c
        Register r4 = new Register("r4");//exit

        List<Register> registers = new ArrayList<Register>();
        registers.add(r1);
        registers.add(r2);
        registers.add(r3);
        registers.add(r4);
        return registers;
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

}
