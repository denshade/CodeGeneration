package laboflieven.ui;

import laboflieven.Program;
import laboflieven.runners.StatementRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lieven on 13-11-2016.
 */
public class DefaultRenderStrategy
{
    private int width;
    private int height;
    private StatementRunner runner;
    private Program program;

    public DefaultRenderStrategy(int width, int height, StatementRunner runner, Program program)
    {

        this.width = width;
        this.height = height;
        this.runner = runner;
        this.program = program;
    }

    public int[][] calculate()
    {
        Map<String, Double> results = new HashMap<>();

        int[][] grid = new int[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                results.put("r0", (double) x);
                results.put("r1", (double) y);
                results.put("r2", (double) y);
                runner.execute(program, results);
                int resultColor = getResultColor(program);
                grid[x][y] = resultColor;
            }
        }
        return grid;
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
}
