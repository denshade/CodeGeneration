package laboflieven.runners;

import laboflieven.Program;

import java.util.Map;

public interface StatementRunner {
    void execute(Program program, Map<String, Double> registerValues);
}
