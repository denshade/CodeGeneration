package laboflieven.instructions.regular;

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

    /**
     * Named vector registers {@code R1..Rn}, same naming as {@link laboflieven.registers.Register#createRegisters(int)}.
     */
    public static List<VectorRegister> createRegisters(int count) {
        List<VectorRegister> registers = new ArrayList<>();
        for (int k = 1; k <= count; k++) {
            registers.add(new VectorRegister("R" + k));
        }
        return registers;
    }

}
