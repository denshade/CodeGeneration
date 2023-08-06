package laboflieven.registers;

import java.util.ArrayList;
import java.util.List;

public class NumberNamingScheme implements RegisterNamingScheme
{

    public List<Register> createRegisters(int count, String prefix)
    {
        List<Register> registers = new ArrayList<Register>();
        for (int k = 1; k <= count; k++)
        {
            registers.add(new Register(prefix + k));
        }
        return registers;
    }

    public List<Register> createRegisters(int count)
    {
        List<Register> registers = new ArrayList<Register>();
        for (int k = 1; k <= count; k++)
        {
            registers.add(new Register("R" + k));
        }
        return registers;
    }
}
