package laboflieven.instructions.logic;

import laboflieven.registers.TemplateRegister;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BruteForceRunner {

    public String loadFromString(String csv, boolean useHeader) {
        var booleanCsvSource = new BooleanCsvSource();
        var s = booleanCsvSource.loadFromCsvString(csv, useHeader);
        var pair = SplitBooleanSource.split(s);
        String line = validateInput(pair);
        if (line != null) return line;

        List<TemplateRegister<Boolean>> registers = TemplateRegister.createAlphabetRegisters(pair.successRecords.get(0).size());
        var evaluator = new Evaluator(registers, pair.successRecords, pair.failRecords);
        var i = new BruteForceIterator(evaluator,2);
        var formula = i.iterate(registers);
        return formula.toString();
    }

    private String validateInput(SplitBooleanSource.ListPair pair) {
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
