package laboflieven.registers;

import java.util.ArrayList;
import java.util.List;

public class TemplateRegister<T> {
    public String name;

    public TemplateRegister(String name)
    {
        this.name = name;
    }
    public TemplateRegister(String name, T value)
    {
        this.name = name;
        this.value = value;
    }

    public T value;
    public String toString()
    {
        return name;
    }

    public static List<TemplateRegister> createRegisters(int count, String prefix)
    {
        List<TemplateRegister> registers = new ArrayList<>();
        for (int k = 1; k <= count; k++)
        {
            registers.add(new TemplateRegister(prefix + k));
        }
        return registers;
    }

    public static List<TemplateRegister<Boolean>> createRegisters(int count)
    {
        List<TemplateRegister<Boolean>> registers = new ArrayList<>();
        for (int k = 1; k <= count; k++)
        {
            registers.add(new TemplateRegister<>("R" + k));
        }
        return registers;
    }

    public static String getName(int index) {
        return String.valueOf((char)('`'+index));
    }
    public static List<TemplateRegister<Boolean>> createAlphabetRegisters(int count) {
        List<TemplateRegister<Boolean>> registers = new ArrayList<>();
        for (int k = 1; k <= count; k++)
        {
            registers.add(new TemplateRegister<>(String.valueOf((char) ('`' + k))));
        }
        return registers;
    }
}
