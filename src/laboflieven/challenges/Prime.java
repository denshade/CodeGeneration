package laboflieven.challenges;

  
import laboflieven.common.Configuration;
import laboflieven.examiners.ProgramFitnessExaminer;
import laboflieven.examiners.ProgramFitnessExaminerInterface;
import laboflieven.instructions.accinstructions.AccInstructionOpcodeEnum;
import laboflieven.instructions.accinstructions.AccInstructionOpcodeEnumBuilder;
import laboflieven.loggers.TimingAccFitnessLogger;
import laboflieven.programiterators.AccPriorityProgramIterator;
import laboflieven.TestcaseInOutParameters;
import laboflieven.runners.AccStatementRunner;

import java.util.ArrayList;
import java.util.List;

public class Prime implements ProgramTemplate
{
//left = R1, leftVector = split(left), swap = left, left = right, right = swap, left = sin(left), left = sum(leftV), R1 = right, left--, left = nand(left, right), R1 = left, R1 = left
    public static void main(String[] args) {

        int curMaxRegisters = 1;
        List<TestcaseInOutParameters> collection = new ArrayList<>();

        for (int i = 2; i < 40; i++) {
            TestcaseInOutParameters p = new TestcaseInOutParameters();
            p.input.put("R1", (double)i);
            p.expectedOutput.put("R1", isPrime(i));
            collection.add(p);
        }
        ProgramFitnessExaminerInterface evaluator = new ProgramFitnessExaminer(collection,new AccStatementRunner());
        //AccInstructionOpcodeEnum[] enums = AccInstructionOpcodeEnumBuilder.make().all().build();
        AccInstructionOpcodeEnum[] enums = AccInstructionOpcodeEnumBuilder.make().with(
                AccInstructionOpcodeEnum.LoadIntoLeftAcc,
                AccInstructionOpcodeEnum.LoadAccLeftIntoVector,
                AccInstructionOpcodeEnum.LoadVectorSumIntoLeft,
                AccInstructionOpcodeEnum.LoadAccLeftIntoRegister,
                AccInstructionOpcodeEnum.LoadVectorIntoLeft,
                AccInstructionOpcodeEnum.Sin,
                AccInstructionOpcodeEnum.Swap,
                AccInstructionOpcodeEnum.Dec,
                AccInstructionOpcodeEnum.Nand
        ).build();
        evaluator.addListener(new TimingAccFitnessLogger(1000));
        var conf = new Configuration();
        conf.setRandomAdded(false);
        conf.setMaxNrInstructions(7).setFitnessExaminer(evaluator).setAccOperations(enums).setNumberOfRegisters(curMaxRegisters);

        var iter = new AccPriorityProgramIterator();
        long start = System.currentTimeMillis();
        System.out.println(iter.iterate(conf));
        System.out.println(System.currentTimeMillis() - start + "ms");
    }
    private static double isPrime(int i)
    {
        for (int k = 2; k < i; k++)
        {
            if (i % k == 0) return 0;
        }
        return 1;
    }

    @Override
    public double run(double[] args) {
        if (args[0] % 3 == 0) return 1;
        if (args[0] % 5 == 0) return 1;
        return 0;
    }


}
