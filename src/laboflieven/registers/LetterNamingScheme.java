package laboflieven.registers;

import java.util.ArrayList;
import java.util.List;

public class LetterNamingScheme implements RegisterNamingScheme{
    @Override
    public List<Register> createRegisters(int count) {
        List<Register> registers = new ArrayList<Register>();
        for (int k = 1; k <= count; k++)
        {
            registers.add(new Register(getName(k)));
        }
        return registers;
    }

    @Override
    public String getName(int index) {
            return String.valueOf((char)('`'+index));
    }
}
