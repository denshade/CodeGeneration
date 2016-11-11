package laboflieven.ui;

import laboflieven.*;
import laboflieven.statements.InstructionEnum;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by Lieven on 5-5-2016.
 */
public class ProgramDrawer
{

    public static final String BRUTE_FORCE = "Brute force";
    public static final String REVERSE_SOLUTION_SEARCH = "Reverse solution search";
    public static final String RANDOM = "Random";
    public static final String ASTAR = "A*";


    public static void main(String[] args)
    {
        JFrame frame = new JFrame("program specifics");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel registers = new JLabel("#registers");
        final JSlider registerCountSlider = new JSlider(1, 10);

        JLabel instructions = new JLabel("Allowed instructions");

        JList allowedInstructions = new JList(InstructionEnum.values());


        registerCountSlider.setMajorTickSpacing(3);
        registerCountSlider.setMinorTickSpacing(1);
        registerCountSlider.setPaintLabels(true);
        registerCountSlider.setPaintTicks(true);
        JPanel drawPanel = new JPanel();
        //drawPanel.setBackground(Color.BLACK);
        drawPanel.setMinimumSize(new Dimension(256, 256));
        JButton button = new JButton("start");
        button.addActionListener(e -> {
            drawPrograms(5, drawPanel);
        });
        GridLayout gridLayout = new GridLayout(3, 2);
        frame.setLayout(gridLayout);
        Container contentPane = frame.getContentPane();
        contentPane.add(registers);
        contentPane.add(registerCountSlider);
        contentPane.add(instructions);
        contentPane.add(allowedInstructions);
        contentPane.add(button);
        contentPane.add(drawPanel);

        frame.pack();
        frame.setVisible(true);

    }

    private static void drawPrograms(int instructionCount, JPanel panel) {
        RealRandomProgramIterator iterator = new RealRandomProgramIterator();
        Program program = iterator.getNextProgram(3, instructionCount);
        StatementRunner runner = new StatementRunner();
        Map<String, Double> results = new HashMap<>();
        for (int x = 0; x < 256;x++)
        {
            for (int y = 0; y < 256;y++)
            {
                results.put("r0", (double)x);
                results.put("r1", (double)y);
                results.put("r2", (double)y);
                runner.execute(program, results);
                double r0 = results.get("r0");
                double r1 = results.get("r1");
                double r2 = results.get("r2");
                int rInt0 = r0 > 255?255:(int)r0;
                int rInt1 = r1 > 255?255:(int)r1;
                int rInt2 = r2 > 255?255:(int)r2;

//                panel.getGraphics().setColor(new Color(rInt0, rInt1, rInt2));
                panel.getGraphics().setColor(Color.GREEN);
                panel.getGraphics().drawRect(x,y,1,1);

                System.out.println(x + "," + y + "=" + results.get("r0") + ", " + results.get("r1") + ", " + results.get("r2"));
            }

        }
    }



}
