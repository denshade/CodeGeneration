package laboflieven.ui;

import laboflieven.*;
import laboflieven.statements.InstructionEnum;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

/**
 * Created by Lieven on 5-5-2016.
 */
public class ProgramSelector
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


        JLabel instructionCount = new JLabel("instructionCount");
        JSlider instructionCountSlider = new JSlider(1, 12);
        instructionCountSlider.setMajorTickSpacing(3);
        instructionCountSlider.setMinorTickSpacing(1);
        instructionCountSlider.setPaintLabels(true);
        instructionCountSlider.setPaintTicks(true);

        registerCountSlider.setMajorTickSpacing(3);
        registerCountSlider.setMinorTickSpacing(1);
        registerCountSlider.setPaintLabels(true);
        registerCountSlider.setPaintTicks(true);
        JLabel strategies = new JLabel("Strategy");
        JComboBox<String> combo = new JComboBox<>(new String[] {ASTAR, BRUTE_FORCE, REVERSE_SOLUTION_SEARCH, RANDOM});

        JLabel boundaries = new JLabel("boundaries. value1,value2,value3,...;solution in register0");
        JTextArea boundariesTextArea = new JTextArea();
        boundariesTextArea.setText("1,0,1600,4;40\n" +
                "1,-700,100000,4;3\n" +
                "1,400,-50000,4;100\n" +
                "4,800,-50000,4;50\n" +
                "-2,1100,-50000,4;50");

        JButton button = new JButton("start");
        button.addActionListener(e -> {
            TextToCriteria converter = new TextToCriteria();
            java.util.List<InOutParameters> collection;
            try {
                collection = converter.parseMultipleStrings(boundariesTextArea.getText());
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage());
                return;
            }
            List<InstructionEnum> selectedValuesList = allowedInstructions.getSelectedValuesList();
            InstructionEnum[] enums = selectedValuesList.toArray(new InstructionEnum[0]);

            ProgramEvaluator evaluator = new ProgramEvaluator(collection);
            System.out.println("Start");
            long start = System.currentTimeMillis();
            if (combo.getSelectedItem().equals(BRUTE_FORCE))
            {

                BruteForceProgramIterator iterator = new BruteForceProgramIterator(evaluator, enums);
                iterator.iterate(registerCountSlider.getValue(), instructionCountSlider.getValue());
            }
            if (combo.getSelectedItem().equals(REVERSE_SOLUTION_SEARCH))
            {
                ReverseProgramIterator iterator = new ReverseProgramIterator(evaluator, enums);
                iterator.iterate(registerCountSlider.getValue(), instructionCountSlider.getValue());
            }
            if (combo.getSelectedItem().equals(RANDOM))
            {
                RandomProgramIterator iterator = new RandomProgramIterator(evaluator, enums);
                iterator.iterate(registerCountSlider.getValue(), instructionCountSlider.getValue());
            }
            if (combo.getSelectedItem().equals(ASTAR))
            {
                PriorityProgramIterator iterator = new PriorityProgramIterator(evaluator, enums);
                iterator.iterate(registerCountSlider.getValue(), instructionCountSlider.getValue());
            }
            long end = System.currentTimeMillis();
            System.out.println("Finished in " + (end - start) + " ms ");
        });
        GridLayout gridLayout = new GridLayout(6, 2);
        frame.setLayout(gridLayout);
        Container contentPane = frame.getContentPane();
        contentPane.add(registers);
        contentPane.add(registerCountSlider);
        contentPane.add(instructions);
        contentPane.add(allowedInstructions);
        contentPane.add(instructionCount);
        contentPane.add(instructionCountSlider);
        contentPane.add(boundaries);
        contentPane.add(boundariesTextArea);
        contentPane.add(strategies);
        contentPane.add(combo);
        contentPane.add(button);


        frame.pack();
        frame.setVisible(true);

    }

}
