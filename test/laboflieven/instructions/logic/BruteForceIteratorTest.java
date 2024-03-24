package laboflieven.instructions.logic;

import laboflieven.registers.TemplateRegister;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BruteForceIteratorTest {

    @Test
    void checkXor() {
        TemplateRegister<Boolean> r1 = new TemplateRegister<>("R1");
        TemplateRegister<Boolean> r2 = new TemplateRegister<>("R2");

        //
        var registers = List.of(r1, r2);
        var evaluator = new Evaluator(registers,
                List.of(
                        List.of(true, false),
                        List.of(false, true)
                ),
                List.of(
                        List.of(true, true),
                        List.of(false, false))
        );
        var i = new BruteForceIterator(evaluator,2);
        var formula = i.iterate(registers);
        System.out.println(formula);
    }

    @Test
    void checkCsvSourceBrute() {
        var booleanCsvSource = new BooleanCsvSource();
        var s = booleanCsvSource.loadFromCsvString("""
    true,true,false
    true,false,true
    false,false,true
    """, false);
        var paramsForSuccess = new ArrayList<List<Boolean>>();
        var paramsForFailure = new ArrayList<List<Boolean>>();
        for (var item : s) {
            var selectedList = paramsForFailure;
            if (item.get(item.size() - 1)) {
                selectedList = paramsForSuccess;
            }
            selectedList.add(item.subList(0, item.size() - 1));
        }

        List<TemplateRegister<Boolean>> registers = TemplateRegister.createAlphabetRegisters(paramsForSuccess.get(0).size());
        var evaluator = new Evaluator(registers, paramsForSuccess, paramsForFailure);
        var i = new BruteForceIterator(evaluator,2);
        var formula = i.iterate(registers);
        System.out.println(formula);
    }

}