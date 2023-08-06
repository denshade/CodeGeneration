package laboflieven.runners;

import laboflieven.Program;
import laboflieven.genericsolutions.GenericSolution;
import laboflieven.registers.Register;

import java.util.List;
import java.util.Map;

public class GenericSolutionAccRunner
{

    public Map<String, Double> execute(GenericSolution program, Map<String, Double> registerValues, List<Register> registerList) {
            AccStatementRunner runner = new AccStatementRunner();
            Program p = new Program(program.programSelector, registerList);
            double selectedProgr = runner.execute(p, registerValues).get("R1");
            Program selectedProgram = new Program(program.programs.get((int) Math.round(selectedProgr)), registerList);
            return runner.execute(selectedProgram, registerValues);
    }
}
