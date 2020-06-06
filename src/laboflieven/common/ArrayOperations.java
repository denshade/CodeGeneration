package laboflieven.common;

import laboflieven.accinstructions.InstructionEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ArrayOperations
{
    public static Object[] anyExcept(Set eq, Object[] objects)
    {
        List<Object> result = new ArrayList<>();
        for (Object obj : objects)
        {
            if (!eq.contains(obj)) {
                result.add(obj);
            }
        }
        return result.toArray();
    }
}
