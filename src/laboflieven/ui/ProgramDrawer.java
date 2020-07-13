package laboflieven.ui;

import laboflieven.*;
import laboflieven.programiterators.RealRandomProgramIterator;
import laboflieven.statements.RegularInstructionOpcode;

import javax.swing.*;
import java.awt.*;

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

        JLabel registers = new JLabel("#instructions");
        final JSlider registerCountSlider = new JSlider(1, 80);

        JLabel instructions = new JLabel("Allowed instructions");

        JList allowedInstructions = new JList(RegularInstructionOpcode.values());
        ProgramRenderPanel programPanel = new ProgramRenderPanel(1000,1000);

        registerCountSlider.setMajorTickSpacing(10);
        registerCountSlider.setMinorTickSpacing(1);
        registerCountSlider.setPaintLabels(true);
        registerCountSlider.setPaintTicks(true);
        JButton button = new JButton("start");
        button.addActionListener(e -> {
            drawPrograms(registerCountSlider.getValue(), programPanel, allowedInstructions);
            JOptionPane.showMessageDialog(frame, programPanel);
        });
        GridLayout gridLayout = new GridLayout(3, 2);
        frame.setLayout(gridLayout);
        Container contentPane = frame.getContentPane();
        contentPane.add(registers);
        contentPane.add(registerCountSlider);
        contentPane.add(instructions);
        contentPane.add(allowedInstructions);
        contentPane.add(button);
        //contentPane.add(programPanel);

        frame.pack();
        frame.setVisible(true);

    }

    private static void drawPrograms(int instructionCount, ProgramRenderPanel programPanel, JList allowedInstructions) {
        RegularInstructionOpcode[] enums = new RegularInstructionOpcode[allowedInstructions.getSelectedValuesList().size()];
        for (int i = 0; i < allowedInstructions.getSelectedValuesList().size(); i++)
        {
            enums[i] = (RegularInstructionOpcode)allowedInstructions.getSelectedValuesList().get(i);
        }
        if (enums.length == 0) return;
        RealRandomProgramIterator iterator = new RealRandomProgramIterator(enums);

        Program program = iterator.getNextProgram(3, instructionCount);
        boolean isAllBlack = programPanel.drawProgram(program);
        while (isAllBlack) {
            program = iterator.getNextProgram(3, instructionCount);
            isAllBlack = programPanel.drawProgram(program);
        }
    }



}
