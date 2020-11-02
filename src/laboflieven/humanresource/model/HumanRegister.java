package laboflieven.humanresource.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by lveeckha on 31/05/2015.
 */
public class HumanRegister
{
    public String name;

    public HumanRegister(String name)
    {
        this.name = name;
    }
    public Integer value;

    public static List<HumanRegister> createRegisters(int numberOfRegisters, String prefix) {
        return IntStream.range(0, numberOfRegisters).mapToObj(k -> new HumanRegister(prefix+k)).collect(Collectors.toList());
    }

    public String toString()
    {
        return name;
    }


    public boolean hasValue() {
        return value != null;
    }
}
