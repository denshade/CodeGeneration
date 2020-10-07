package laboflieven.runners;

import laboflieven.Program;

import java.util.Map;

public interface StatementRunner {
    Map<String, Double> execute(Program program, Map<String, Double> registerValues);
}
