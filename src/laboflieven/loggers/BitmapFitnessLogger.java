package laboflieven.loggers;

import laboflieven.InstructionMark;
import laboflieven.accinstructions.AccRegisterInstruction;
import laboflieven.common.AccInstructionOpcode;
import laboflieven.statements.DualRegisterInstruction;
import laboflieven.statements.Instruction;
import laboflieven.statements.SingleRegisterInstruction;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class BitmapFitnessLogger implements FitnessLogger
{
    private final File file;
    private final int nrInstruction;
    private final int nrRegisters;
    int maxX = 0;
    int maxY = 0;
    Map<Point, Double> elements = new HashMap<>();
    public BitmapFitnessLogger(File file, int nrInstruction, int nrRegisters) {
        this.file = file;
        this.nrInstruction = nrInstruction;
        this.nrRegisters = nrRegisters;
    }

    @Override
    public void addFitness(List<InstructionMark> instructions, int nrInstructionOld, int nrRegistersOld, double error) {
        BigInteger[] numbers = getXandY(instructions, nrInstruction, nrRegisters);
        Point p = new Point();
        p.x  = numbers[0].intValue();
        p.y  = numbers[1].intValue();
        if (p.x > maxX) maxX = p.x;
        if (p.y > maxY) maxY = p.y;

        elements.put(p, error);
        if (error < 0.000001) System.out.println(instructions);
    }

    public void finish() throws IOException {

        final BufferedImage res = new BufferedImage( maxX + 1, maxY + 1, BufferedImage.TYPE_INT_RGB );
        double max = elements.values().stream().filter(Double::isFinite).max(Comparator.naturalOrder()).get();
        for (Entry<Point, Double> el : elements.entrySet())
        {
            Point p = el.getKey();
            if (el.getValue() < 1){
                res.setRGB(p.x, p.y, Color.WHITE.getRGB());
            }else {
                //float relative = (float)(el.getValue() / max) * 255;
                double value = el.getValue();
                double relative = 255;
                if (Double.isFinite(value)) {
                    //relative = (float)Math.log10(el.getValue());
                    relative = Math.min(255, el.getValue());
                }
                res.setRGB(p.x, p.y, new Color((int)(255-relative), 0, 0).getRGB());
                if (el.getValue() < 1) {
                    res.setRGB(p.x, p.y, new Color(255, 255, 255).getRGB());
                }
                //res.setRGB(p.x, p.y, new Color(1-relative, 0, relative).getRGB());
            }
        }
        ImageIO.write(res, "bmp", file);


    }


    public BigInteger[] getXandY(List<InstructionMark> instructions, int nrInstruction, int nrRegisters)
    {
        BigInteger sumInstructX = BigInteger.ZERO;
        BigInteger instructionMultiplier = BigInteger.ONE;
        BigInteger nrInstructionMult = BigInteger.valueOf(nrInstruction + 1);
        for (InstructionMark instruction : instructions)
        {
            int instructNr;
            if (instruction.getInstructionOpcode() instanceof AccInstructionOpcode)
            {
                instructNr = ((AccInstructionOpcode)instruction.getInstructionOpcode()).getEnumer().ordinal() + 1;
            } else {
                switch(instruction.getClass().getSimpleName())
                {
                    case "Add" : instructNr = 1; break;
                    case "Cos" : instructNr = 2; break;
                    case "Div" : instructNr = 3; break;
                    case "Invert" : instructNr = 4; break;
                    case "Log" : instructNr = 5; break;
                    case "Mod" : instructNr = 6; break;
                    case "Move" : instructNr = 7; break;
                    case "Mul" : instructNr = 8; break;
                    case "Nand" : instructNr = 9; break;
                    case "Sin" : instructNr = 10; break;
                    case "Sqrt" : instructNr = 11; break;
                    case "Sub" : instructNr = 12; break;
                    default: throw new RuntimeException("Unknown class " + instruction.getClass().toString());
                }
            }
            sumInstructX = sumInstructX.add(BigInteger.valueOf(instructNr).multiply(instructionMultiplier));
            instructionMultiplier = instructionMultiplier.multiply(nrInstructionMult);
        }

        BigInteger sumRegister = BigInteger.ZERO;
        BigInteger registerMultiplier = BigInteger.ONE;
        BigInteger nrRegisterMult = BigInteger.valueOf(nrRegisters);
        for (InstructionMark instruction : instructions)
        {
            String source = "";
            String dest = "";
            if (instruction instanceof DualRegisterInstruction)
            {
                source = ((DualRegisterInstruction) instruction).source.name;
                dest = ((DualRegisterInstruction) instruction).destination.name;
            } else if (instruction instanceof SingleRegisterInstruction){
                source = "R0";
                dest = ((SingleRegisterInstruction) instruction).destination.name;
            }
            else if (instruction instanceof laboflieven.accinstructions.SingleRegisterInstruction)
            {
                source = ((laboflieven.accinstructions.SingleRegisterInstruction) instruction).getRegister().name;
                dest = "R0";
            } else if (instruction instanceof AccRegisterInstruction)
            {
                dest = "R0";
                source = "R0";
            }
            int sourceNr = registerToInt(source);
            int destNr = registerToInt(dest);
            sumRegister = sumRegister.add(BigInteger.valueOf(sourceNr).multiply(registerMultiplier));
            registerMultiplier = registerMultiplier.multiply(nrRegisterMult);
            sumRegister = sumRegister.add(BigInteger.valueOf(destNr).multiply(registerMultiplier));
            registerMultiplier = registerMultiplier.multiply(nrRegisterMult);
        }
        return new BigInteger[]{sumInstructX, sumRegister};
    }


    public int registerToInt(String source) {
        int sourceNr;
        switch(source.toUpperCase())
        {
            case "R0" : sourceNr = 0; break;
            case "R1" : sourceNr = 1; break;
            case "R2" : sourceNr = 2; break;
            case "R3" : sourceNr = 3; break;
            case "R4" : sourceNr = 4; break;
            default: throw new RuntimeException("Unknown register " + source);
        }
        return sourceNr;
    }
}
