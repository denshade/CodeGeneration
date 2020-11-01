package laboflieven.challenges;

import laboflieven.InOutParameters;
import laboflieven.accinstructions.AccInstructionOpcodeEnum;
import laboflieven.common.Configuration;
import laboflieven.examiners.ProgramFitnessExaminer;
import laboflieven.examiners.ProgramFitnessExaminerInterface;
import laboflieven.programiterators.BruteForceProgramIterator;
import laboflieven.programiterators.GeneralBruteForceProgramIterator;
import laboflieven.recursionheuristics.NoInvertedHeuristic;
import laboflieven.runners.AccStatementRunner;
import laboflieven.runners.RegularStatementRunner;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CosDifferenceFinder implements ProgramTemplate
{
    public static double distance(double lat1) {
        return Math.sin(lat1) ;
    }


    public static void main(String[] args) throws IOException {
        int curMaxRegisters = 1;
        List<InOutParameters> collection = TestCases.getTestCases(new CosDifferenceFinder(), TestCases.getExampleInput1D(3,.01),curMaxRegisters);
        collection = differentiate(collection);

        File f = new File("c:\\temp\\test.csv");
        ProgramFitnessExaminerInterface evaluator = new ProgramFitnessExaminer(collection, new AccStatementRunner());
        var conf = new Configuration();
        conf.setMaxNrInstructions(5);
        conf.setFitnessExaminer(evaluator);
        conf.setNumberOfRegisters(curMaxRegisters);
        conf.setAccOperations( new AccInstructionOpcodeEnum[] {
                AccInstructionOpcodeEnum.AccLeftPull,
                AccInstructionOpcodeEnum.AccLeftPush,
                AccInstructionOpcodeEnum.Cos,
        });

        GeneralBruteForceProgramIterator iter = new GeneralBruteForceProgramIterator();
        long start = System.currentTimeMillis();
        iter.iterate(conf);
        //evaluator.writeAndClose();
        System.out.println(System.currentTimeMillis() - start + "ms");

        /*iter = new BruteForceProgramIterator(evaluator, new AlwaysRecursionHeuristic());
        start = System.currentTimeMillis();
        iter.iterate(curMaxRegisters, 4);
        //evaluator.writeAndClose();
        */
        System.out.println( "19000ms");

    }

    private static List<InOutParameters> differentiate(List<InOutParameters> collection) {
        List<InOutParameters> result = new ArrayList<>();

        for (int i = 0; i < collection.size() - 1; i++)
        {
            InOutParameters param = new  InOutParameters();
            param.input =  collection.get(i).input;
            Map<String, Double> stringDoubleMap = new HashMap<String, Double>();
            Map<String, Double> currentValues = collection.get(i).expectedOutput;
            Map<String, Double> nextValues = collection.get(i + 1).expectedOutput;
            for (String r : currentValues.keySet())
            {
                stringDoubleMap.put(r,nextValues.get(r) - currentValues.get(r));
            }
            param.expectedOutput = stringDoubleMap;
            result.add(param);

        }
        return result;
    }

    @Override
    public double run(double[] args) {
        return distance(args[0]);
    }
}
