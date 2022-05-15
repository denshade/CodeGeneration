package laboflieven.loggers;

import laboflieven.InstructionMark;
import laboflieven.common.InstructionOpcode;
import laboflieven.functional.loggers.InstructionIndexPair;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Array;
import java.util.*;
import java.util.List;

public class ErrorCsvFileFitnessLogger implements FitnessLogger
{
    FileWriter writer;
    private InstructionIndexPairContainer container;
    public ErrorCsvFileFitnessLogger(File file, List<InstructionOpcode> opcodes) throws IOException {
        writer = new FileWriter(file);
        container = new InstructionIndexPairContainer(opcodes);

    }

    @Override
    public void addFitness(List<InstructionMark> instructions, int nrInstruction, int nrRegisters, double error) {
        container.addFitness(instructions, nrRegisters, error);
    }

    public void finish() throws IOException {
        Map<Point, Double> elements = container.getElements();
        Map<Integer, Double> values = new HashMap<>(elements.values().size());
        for (Map.Entry<Point, Double> el : elements.entrySet()) {
            int x = el.getKey().x;
            Double value = el.getValue();
            values.putIfAbsent(x, value);
            if (values.get(x) > value) {
                values.put(x, value);
            }
        }
        for (Integer value : new TreeSet<>(values.keySet())) {
            writer.write(value + "," + values.get(value)+"\n");
        }
        writer.close();
    }

}
