package statements;

/**
 * Created by lveeckha on 31/05/2015.
 */
public class Register
{
    private String name;

    public Register(String name)
    {
        this.name = name;
    }
    public double value;
    public String toString()
    {
        return name + " "+value;
    }
}
