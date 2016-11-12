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
        ProgramRenderPanel programPanel = new ProgramRenderPanel(256,256);

        registerCountSlider.setMajorTickSpacing(3);
        registerCountSlider.setMinorTickSpacing(1);
        registerCountSlider.setPaintLabels(true);
        registerCountSlider.setPaintTicks(true);
        JButton button = new JButton("start");
        button.addActionListener(e -> {
            drawPrograms(5, programPanel);
        });
        GridLayout gridLayout = new GridLayout(3, 2);
        frame.setLayout(gridLayout);
        Container contentPane = frame.getContentPane();
        contentPane.add(registers);
        contentPane.add(registerCountSlider);
        contentPane.add(instructions);
        contentPane.add(allowedInstructions);
        contentPane.add(button);
        contentPane.add(programPanel);

        frame.pack();
        frame.setVisible(true);

    }

    private static void drawPrograms(int instructionCount, ProgramRenderPanel programPanel) {
        RealRandomProgramIterator iterator = new RealRandomProgramIterator();
        Program program = iterator.getNextProgram(3, instructionCount);
        programPanel.drawProgram(program);
    }



}
