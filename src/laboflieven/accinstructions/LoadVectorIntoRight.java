package laboflieven.accinstructions;

import laboflieven.common.InstructionOpcode;
import laboflieven.statements.Register;
import laboflieven.statements.VectorRegister;
import org.apache.commons.math3.primes.Primes;

import java.util.Vector;

public class LoadVectorIntoRight extends AccRegisterInstruction {
    @Override
    public InstructionOpcode getInstructionOpcode() {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public Integer execute(Register left, Register right, int ip) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public Integer execute(Register left, Register right, VectorRegister vectorLeft, VectorRegister vectorRight, int ip) {
        right.value = convertVectorToDouble(vectorRight.value);
        return null;
    }

    static double convertVectorToDouble(Vector<Double> vector) {
        double val = 1;
        int selectedElement = 0;
        int i = 2;
        while (selectedElement < vector.size())
        {
            if (Primes.isPrime(i)) {
                val *= Math.pow(i, vector.get(selectedElement));
                selectedElement++;
            }
            i++;
        }
        return val;
    }

    @Override
    public String toString() {
        return null;
    }
}
