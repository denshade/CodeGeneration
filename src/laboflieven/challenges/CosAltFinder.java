package laboflieven.challenges;

import laboflieven.InOutParameters;
import laboflieven.accinstructions.AccInstructionOpcodeEnum;
import laboflieven.common.Configuration;
import laboflieven.examiners.ProgramFitnessExaminer;
import laboflieven.examiners.ProgramFitnessExaminerInterface;
import laboflieven.programiterators.BruteForceProgramIterator;
import laboflieven.programiterators.GeneralBruteForceProgramIterator;
import laboflieven.recursionheuristics.HashedResultsHeuristic;
import laboflieven.recursionheuristics.NoInvertedHeuristic;
import laboflieven.runners.AccStatementRunner;
import laboflieven.runners.RegularStatementRunner;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public class CosAltFinder implements ProgramTemplate
{
    public static double distance(double lat1) {
        return Math.sin(lat1);
    }


    public static void main(String[] args) throws IOException {
        int curMaxRegisters = 1;
        List<InOutParameters> collection = TestCases.getTestCases(new CosAltFinder(), TestCases.getExampleInput1D(50,10),curMaxRegisters);

        Configuration configuration = new Configuration();
        ProgramFitnessExaminerInterface evaluator = new ProgramFitnessExaminer(collection, new AccStatementRunner());
        configuration.setFitnessExaminer(evaluator);
        configuration.setMaxNrInstructions(7);
        configuration.setNumberOfRegisters(1);
        configuration.setHeuristic(new HashedResultsHeuristic(collection, new AccStatementRunner()));
        configuration.setAccOperations(AccInstructionOpcodeEnum.anyExcept(Set.of(AccInstructionOpcodeEnum.Sin)));
        GeneralBruteForceProgramIterator iter = new GeneralBruteForceProgramIterator(evaluator, AccInstructionOpcodeEnum.anyExcept(Set.of(AccInstructionOpcodeEnum.Sin)));
        long start = System.currentTimeMillis();
        iter.iterate(configuration);
        //evaluator.writeAndClose();
        System.out.println(System.currentTimeMillis() - start + "ms");

    }

    @Override
    public double run(double[] args) {
        return distance(args[0]);
    }
}
