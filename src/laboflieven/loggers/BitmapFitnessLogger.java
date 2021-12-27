package laboflieven.loggers;

import laboflieven.InstructionMark;
import laboflieven.accinstructions.AccRegisterInstruction;
import laboflieven.common.AccInstructionOpcode;
import laboflieven.common.RegularInstructionOpcode;
import laboflieven.functional.loggers.RegistersBigIntegerIndex;
import laboflieven.statements.DualRegisterInstruction;
import laboflieven.statements.SingleRegisterInstruction;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class BitmapFitnessLogger implements FitnessLogger
{
    private final File file;
    private final int nrRegisters;
    private final List opcodes;
    int maxX = 0;
    int maxY = 0;
    Map<Point, Double> elements = new HashMap<>();

    public BitmapFitnessLogger(File file, int nrRegisters, List opcodes) {
        this.file = file;
        this.nrRegisters = nrRegisters;
        this.opcodes = opcodes;
    }

    @Override
    public void addFitness(List<InstructionMark> instructions, int nrInstructionOld, int nrRegistersOld, double error) {
        BigInteger[] numbers = getXandY(instructions, nrRegisters);
        Point p = new Point();
        p.x  = numbers[0].intValue();
        p.y  = numbers[1].intValue();
        if (p.x > maxX) maxX = p.x;
        if (p.y > maxY) maxY = p.y;

        elements.put(p, error);
    }

    public void finish() throws IOException {

        final BufferedImage res = new BufferedImage( maxX + 1, maxY + 1, BufferedImage.TYPE_INT_RGB );
        Graphics2D graphics = res.createGraphics();
        graphics.setPaint(new Color(0,0,255));
        graphics.fillRect(0,0, maxX + 1, maxY + 1);
        //double max = elements.values().stream().filter(Double::isFinite).max(Comparator.naturalOrder()).get();
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
                //res.setRGB(p.x, p.y, new Color(1-relative, 0, relative).getRGB());
            }
        }
        ImageIO.write(res, "bmp", file);


    }

    /**
     * Yields an X (instruction position) and Y (register position).
     * @param instructions
     * @param nrRegisters
     * @return
     */
    public BigInteger[] getXandY(List<InstructionMark> instructions, int nrRegisters)
    {
        BigInteger sumInstructX = BigInteger.ZERO;
        BigInteger instructionMultiplier = BigInteger.ONE;
        BigInteger nrInstructionMult;
        nrInstructionMult = BigInteger.valueOf(opcodes.size() + 1);
        for (InstructionMark instruction : instructions)
        {
            int instructNr = opcodes.indexOf(instruction.getInstructionOpcode().getEnumeration()) + 1;
            if (instructNr == 0) {
             throw new RuntimeException("Unknown class " + instruction.getClass().toString());
            }
            sumInstructX = sumInstructX.add(BigInteger.valueOf(instructNr).multiply(instructionMultiplier));
            instructionMultiplier = instructionMultiplier.multiply(nrInstructionMult);
        }
        return new BigInteger[]{sumInstructX, new RegistersBigIntegerIndex(instructions, nrRegisters).getSumRegister()};
    }
}
