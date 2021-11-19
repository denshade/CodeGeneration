package laboflieven.accinstructions;

import laboflieven.common.AccInstructionOpcode;
import laboflieven.common.InstructionOpcode;
import laboflieven.statements.Register;
import laboflieven.statements.VectorRegister;
import org.apache.commons.math3.primes.Primes;

import java.util.Vector;

public class LoadAccRightIntoVector extends AccRegisterInstruction {
    @Override
    public InstructionOpcode getInstructionOpcode() {
        return new AccInstructionOpcode(AccInstructionOpcodeEnum.LoadAccRightIntoVector);
    }

    @Override
    public Integer execute(Register left, Register right, int ip) {
        throw new RuntimeException("Not implemented");
    }
    @Override
    public Integer execute(Register left, Register right, VectorRegister vectorLeft, VectorRegister vectorRight, int ip) {
        vectorRight.value = getVectorFromValue(right.value);
        return null;
    }

    static Vector<Double> getVectorFromValue(double val) {
        if (val % 1 != 0 || val > 1000) {
            return new Vector<>();
        }
        Vector<Double> values = new Vector<>();
        for (int i = 2; i <= val; i++)
        {
            if (Primes.isPrime(i)) {
                int counter = 0;
                while(val % i == 0)
                {
                    counter++;
                    val /= i;
                }
                values.add((double)counter);
            }
        }
        return values;
    }

    @Override
    public String toString() {
        return  "rightVector = split(right)";
    }
}
