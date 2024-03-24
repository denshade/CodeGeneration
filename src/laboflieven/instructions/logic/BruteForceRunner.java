package laboflieven.instructions.logic;

import laboflieven.registers.TemplateRegister;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class BruteForceRunner {

    public String loadFromString(String csv, boolean useHeader) {
        var booleanCsvSource = new BooleanCsvSource();
        try {
            var s = booleanCsvSource.loadFromCsvString(csv, useHeader);
            var pair = SplitBooleanSource.split(s);
            String line = validateInput(pair);
            if (line != null) return line;

            List<TemplateRegister<Boolean>> registers = TemplateRegister.createAlphabetRegisters(pair.successRecords.get(0).size());
            if (useHeader) {
                registers = Arrays.stream(csv.split("\n")[0].split(",")).map(r -> new TemplateRegister<Boolean>(r)).collect(Collectors.toList());
                removeOutcomeRegister(registers);
            }
            var evaluator = new Evaluator(registers, pair.successRecords, pair.failRecords);
            var i = new BruteForceIterator(evaluator,2);
            var formula = i.iterate(registers);
            return formula.toString();
        } catch (IllegalArgumentException exception) {
            return exception.toString();
        }
    }

    public static void main(String[] args) {
        System.out.println("Reading data from stdin");
        String data =  new BufferedReader(new InputStreamReader(System.in)).lines().collect(Collectors.joining("\n"));
        System.out.println(new BruteForceRunner().loadFromString(data, true));
    }

    private void removeOutcomeRegister(List<TemplateRegister<Boolean>> registers) {
        registers.remove(registers.size() - 1);
    }

    private String validateInput(SplitBooleanSource.ListPair pair) {
        if (pair.successRecords.isEmpty()) {
            throw new IllegalArgumentException("No success records found. Just return false always.");
        }
        if (pair.failRecords.isEmpty()) {
            throw new IllegalArgumentException("No fail records found. Just return true always.");
        }
        Set<String> alreadyKnownPairs = new HashSet<>();
        for (var line: pair.successRecords) {
            if (alreadyKnownPairs.contains(line.toString())) {
                return "Conflicting pair. This pair is a duplicate: " + line;
            }
            alreadyKnownPairs.add(line.toString());
        }
        for (var line: pair.failRecords) {
            if (alreadyKnownPairs.contains(line.toString())) {
                return "Conflicting pair. This pair is a duplicate: " + line;
            }
            alreadyKnownPairs.add(line.toString());
        }
        return null;
    }
}
