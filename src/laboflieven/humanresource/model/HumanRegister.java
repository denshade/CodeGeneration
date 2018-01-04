package laboflieven.humanresource.model;

import java.util.ArrayList;
import java.util.List;

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
    public String toString()
    {
        return name;
    }


    public boolean hasValue() {
        return value != null;
    }
}
