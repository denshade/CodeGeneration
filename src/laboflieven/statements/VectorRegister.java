package laboflieven.statements;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by lveeckha on 31/05/2015.
 */
public class VectorRegister
{
    public String name;

    public VectorRegister(String name)
    {
        this.name = name;
        value= new Vector<>();
    }
    public Vector<Double> value;
    public String toString()
    {
        return name;
    }


}
