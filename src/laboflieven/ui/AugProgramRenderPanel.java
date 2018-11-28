package laboflieven.ui;

import laboflieven.Program;
import laboflieven.StatementRunner;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Lieven on 12-11-2016.
 */
public class AugProgramRenderPanel extends JPanel {


    private BufferedImage canvas;

    private int width;
    private int height;


    public AugProgramRenderPanel(int width, int height) {
        this.width = width;
        this.height = height;
        canvas = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        fillCanvas(Color.BLUE);
        drawRect(Color.RED, 0, 0, width / 2, height / 2);
    }

    public Dimension getPreferredSize() {
        return new Dimension(canvas.getWidth(), canvas.getHeight());
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(canvas, null, null);
    }


    public void fillCanvas(Color c) {
        int color = c.getRGB();
        for (int x = 0; x < canvas.getWidth(); x++) {
            for (int y = 0; y < canvas.getHeight(); y++) {
                canvas.setRGB(x, y, color);
            }
        }
        repaint();
    }

    public boolean drawProgram(Program program)
    {
        System.out.println(program.getInstructions());
        StatementRunner runner = new StatementRunner();
        DefaultRenderStrategy strategy = new DefaultRenderStrategy(width, height, runner, program);
        int[][] grid = strategy.calculate();
        long sumR = 0;
        long sumG = 0;
        long sumB = 0;
        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++)
            {
                sumR += grid[x][y]%255;
                sumG += (grid[x][y]/255)%255;
                sumB += (grid[x][y]/65536)%255;
            }

        long avgR = sumR/(width*height);
        long avgG = sumG/(width*height);
        long avgB = sumB/(width*height);

        System.out.println(avgR + " " + avgG + " " + avgB);

        if (avgB + avgG + avgR < 10) return true;
        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++)
                canvas.setRGB(x, y, grid[x][y]);



        repaint();
        return false;
    }

    private int getResultColor(Program program) {
        double r0 = Math.abs(program.getRegisters().get(0).value);
        double r1 = Math.abs(program.getRegisters().get(1).value);
        double r2 = Math.abs(program.getRegisters().get(2).value);

        int rInt0 = r0 > 255?255:(int)r0;
        int rInt1 = r1 > 255?255:(int)r1;
        int rInt2 = r2 > 255?255:(int)r2;
        return rInt0 + rInt1 * 256 + rInt2 * 256 * 256;
    }

    public void drawLine(Color c, int x1, int y1, int x2, int y2) {
        // Implement line drawing
        repaint();
    }

    public void drawRect(Color c, int x1, int y1, int width, int height) {
        int color = c.getRGB();
        // Implement rectangle drawing
        for (int x = x1; x < x1 + width; x++) {
            for (int y = y1; y < y1 + height; y++) {
                canvas.setRGB(x, y, color);
            }
        }
        repaint();
    }

    public void drawOval(Color c, int x1, int y1, int width, int height) {
        // Implement oval drawing
        repaint();
    }


}
