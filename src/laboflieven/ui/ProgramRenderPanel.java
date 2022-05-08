package laboflieven.ui;

import laboflieven.Program;
import laboflieven.runners.RegularStatementRunner;
import laboflieven.runners.StatementRunner;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Lieven on 12-11-2016.
 */
public class ProgramRenderPanel extends JPanel {


    private final BufferedImage canvas;

    private final int width;
    private final int height;


    public ProgramRenderPanel(int width, int height) {
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
        StatementRunner runner = new RegularStatementRunner();
        DefaultRenderStrategy strategy = new DefaultRenderStrategy(width, height, runner, program);
        int[][] grid = strategy.calculate();
        int sumR = 0;
        int sumG = 0;
        int sumB = 0;
        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++)
            {
                sumR = Math.max(sumR, grid[x][y]%255);
                sumG = Math.max(sumG, (grid[x][y]/255)%255);
                sumB = Math.max(sumB, (grid[x][y]/65536)%255);
            }


        System.out.println(sumR + " " + sumG + " " + sumB);

        if (sumR + sumG + sumB < 1) return true;
        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++)
                canvas.setRGB(x, y, getRgb(grid[x][y], sumR, sumG, sumB));



        repaint();
        return false;
    }

    public int getRgb(int color, int maxR, int maxG, int maxB)
    {
        int r = color%255;
        int g = (color/256)%255;
        int b = (color/65536)%255;
        int newR = r;
        if (r!= 0)
        {
            newR = (255 / maxR)*r;
        }
        int newG = g;
        if (g != 0)
        {
            newG = (255 / maxG)*g;
        }
        int newB = b;
        if (b!= 0)
        {
            newB = (255 / maxB)*b;
        }
        return newR + newG*256 + newB * 65536;
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
