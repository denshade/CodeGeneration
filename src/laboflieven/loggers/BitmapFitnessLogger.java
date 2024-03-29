package laboflieven.loggers;

import laboflieven.InstructionMark;
import laboflieven.common.InstructionOpcode;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class BitmapFitnessLogger implements FitnessLogger
{
    private final File file;
    private InstructionIndexPairContainer container;

    public BitmapFitnessLogger(File file, List<InstructionOpcode> opcodes) {
        this.file = file;
        container = new InstructionIndexPairContainer(opcodes);
    }

    @Override
    public void addFitness(List<InstructionMark> instructions, int nrInstructionOld, int nrRegistersOld, double error) {
        container.addFitness(instructions, nrRegistersOld, error);
    }

    public void finish() throws IOException {
        int maxX = container.getMaxX();
        int maxY = container.getMaxY();
        final BufferedImage res = new BufferedImage(maxX + 1, maxY + 1, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = res.createGraphics();
        graphics.setPaint(new Color(0, 0, 255));
        graphics.fillRect(0, 0, maxX + 1, maxY + 1);
        Map<Point, Double> elements = container.getElements();
        double max = elements.values().stream().filter(Double::isFinite).max(Comparator.naturalOrder()).get();
        for (Entry<Point, Double> el : elements.entrySet()) {
            Point p = el.getKey();
            if (el.getValue() < 1) {
                res.setRGB(p.x, p.y, Color.WHITE.getRGB());
            } else {
                double value = el.getValue();
                double relative = 255;
                if (Double.isFinite(value)) {
                    //relative = (float)Math.log10(el.getValue());
                    relative = (float)(el.getValue() / max) * 255;
                //    relative = Math.min(255, el.getValue());
                }
                res.setRGB(p.x, p.y, new Color((int) (255 - relative), 0, 0).getRGB());
                //res.setRGB(p.x, p.y, new Color(1-relative, 0, relative).getRGB());
            }
        }
        ImageIO.write(res, "bmp", file);
    }
}
