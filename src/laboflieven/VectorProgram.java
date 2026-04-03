package laboflieven;

import laboflieven.instructions.regular.VectorRegister;
import laboflieven.registers.Register;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A program whose storage is {@link VectorRegister}s only. Existing acc instructions still use
 * scalar {@link Register} operands at runtime; those scalars are mapped to the <em>first element</em>
 * of each vector register for the duration of a run (other elements are left unchanged by scalar ops).
 */
public class VectorProgram {

    private final List<InstructionMark> instructions;
    private final List<VectorRegister> registers;

    public VectorProgram(List<InstructionMark> instructions, List<VectorRegister> registers) {
        this.instructions = instructions;
        this.registers = registers;
    }

    public List<InstructionMark> getInstructions() {
        return instructions;
    }

    public List<VectorRegister> getRegisters() {
        return registers;
    }

    public void initializeVectors(Map<String, List<Double>> values) {
        for (VectorRegister vr : registers) {
            fillVector(vr, values.get(vr.name));
        }
    }

    /**
     * Temporary scalar registers for {@link Program} binding — same names and order as {@link #getRegisters()}.
     */
    public List<Register> createScalarRegistersForBinding() {
        List<Register> list = new ArrayList<>(registers.size());
        for (VectorRegister vr : registers) {
            list.add(new Register(vr.name));
        }
        return list;
    }

    public Map<String, Double> firstElementsAsScalarMap() {
        Map<String, Double> m = new HashMap<>(registers.size());
        for (VectorRegister vr : registers) {
            m.put(vr.name, vr.value.isEmpty() ? 0.0 : vr.value.get(0));
        }
        return m;
    }

    /**
     * Writes each bound scalar register's value into index 0 of the corresponding vector register.
     */
    public void writeFirstElementsFromRegisters(List<Register> boundRegisters) {
        for (int i = 0; i < registers.size(); i++) {
            VectorRegister vr = registers.get(i);
            Register r = boundRegisters.get(i);
            if (vr.value.isEmpty()) {
                vr.value.add(r.value);
            } else {
                vr.value.set(0, r.value);
            }
        }
    }

    private static void fillVector(VectorRegister reg, List<Double> values) {
        reg.value.clear();
        if (values != null) {
            reg.value.addAll(values);
        }
    }
}
