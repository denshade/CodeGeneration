package laboflieven.common;

import laboflieven.accinstructions.InstructionEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ArrayOperations<T>
{
    public List<T> anyExcept(Set<T> eq, T[] objects)
    {
        List<T> result = new ArrayList<>();
        for (T obj : objects)
        {
            if (!eq.contains(obj)) {
                result.add(obj);
            }
        }
        return result;
    }
}
