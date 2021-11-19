package laboflieven.challenges;

import laboflieven.*;
import laboflieven.accinstructions.AccInstructionOpcodeEnum;
import laboflieven.accinstructions.AccInstructionOpcodeEnumBuilder;
import laboflieven.common.Configuration;
import laboflieven.examiners.ProgramFitnessExaminer;
import laboflieven.loggers.BitmapFitnessLogger;
import laboflieven.loggers.RandomSysOutAccFitnessLogger;
import laboflieven.programiterators.GeneralBruteForceProgramIterator;
import laboflieven.runners.AccStatementRunner;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Max implements ProgramTemplate
{
    /*
Found a program: [left = R1,  right = R2,  Jump to start if L >= R , R1 = right]

     */
    public static void main(String[] args) {
        int curMaxRegisters = 2;
        List<double[]> points = new ArrayList<>();
        points.add(new double[] { 1, 10});
        points.add(new double[] { 10, 10});
        points.add(new double[] { 100, 10});
        List<TestcaseInOutParameters> collection = TestCases.getTestCases(new Max(), points.toArray(new double[0][0]),curMaxRegisters);
        ProgramFitnessExaminer evaluator = new ProgramFitnessExaminer(collection, new AccStatementRunner());
        evaluator.addListener(new RandomSysOutAccFitnessLogger(10000));
        AccInstructionOpcodeEnum[] opcodeEnums = AccInstructionOpcodeEnumBuilder.make().with(AccInstructionOpcodeEnum.LoadIntoLeftAcc, AccInstructionOpcodeEnum.LoadIntoRightAcc, AccInstructionOpcodeEnum.JumpIfGteStart, AccInstructionOpcodeEnum.LoadAccRightIntoRegister).build();
        /*        AccInstructionOpcodeEnum[] opcodeEnums = AccInstructionOpcodeEnumBuilder.make().anyExcept(Set.of(
                AccInstructionOpcodeEnum.LoadAccLeftIntoVector,
                AccInstructionOpcodeEnum.LoadAccRightIntoVector,
                AccInstructionOpcodeEnum.LoadVectorIntoLeft,
                AccInstructionOpcodeEnum.LoadVectorIntoRight
        )).build();*/
        BitmapFitnessLogger bmpLogger = new BitmapFitnessLogger(new File("c:\\temp\\test.bmp"), 2, List.of(opcodeEnums));
        evaluator.addListener(bmpLogger);
        var conf = new Configuration();
        conf.setMaxNrInstructions(4).setFitnessExaminer(evaluator).setAccOperations(opcodeEnums).setNumberOfRegisters(curMaxRegisters);
        GeneralBruteForceProgramIterator iter = new GeneralBruteForceProgramIterator();
        long start = System.currentTimeMillis();
        var p = iter.iterate(conf);
        try {
            bmpLogger.finish();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(System.currentTimeMillis() - start + "ms");
        System.out.println(p);
    }

    @Override
    public double run(double[] args) {
        return Math.max(args[0], args[1]);
    }


}
