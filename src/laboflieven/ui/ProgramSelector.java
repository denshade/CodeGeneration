package laboflieven.ui;

import laboflieven.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

/**
 * Created by Lieven on 5-5-2016.
 */
public class ProgramSelector
{

    public static final String BRUTE_FORCE = "Brute force";
    public static final String REVERSE_SOLUTION_SEARCH = "Reverse solution search";
    public static final String RANDOM = "Random";

    public static void main(String[] args)
    {
        JFrame frame = new JFrame("program specifics");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel registers = new JLabel("#registers");
        final JSlider registerCountSlider = new JSlider(1, 10);

        JLabel instructions = new JLabel("Allowed instructions");
        JList allowedInstructions = new JList(new String[] {
                "+", "-", "*", "/", "%", "sqrt", "move"
        });

        JLabel instructionCount = new JLabel("instructionCount");
        final JSlider instructionCountSlider = new JSlider(1, 10);

        instructionCountSlider.setMajorTickSpacing(3);
        instructionCountSlider.setMinorTickSpacing(1);
        instructionCountSlider.setPaintLabels(true);
        instructionCountSlider.setPaintTicks(true);

        registerCountSlider.setMajorTickSpacing(3);
        registerCountSlider.setMinorTickSpacing(1);
        registerCountSlider.setPaintLabels(true);
        registerCountSlider.setPaintTicks(true);
        JLabel strategies = new JLabel("Strategy");
        final JComboBox<String> combo = new JComboBox<>(new String[] {BRUTE_FORCE, REVERSE_SOLUTION_SEARCH, RANDOM});

        JLabel boundaries = new JLabel("boundaries. value1,value2,value3,...;solution in register0");
        final JTextArea boundariesTextArea = new JTextArea();

        JButton button = new JButton("start");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TextToCriteria converter = new TextToCriteria();
                java.util.List<InOutParameters> collection;
                try {
                    collection = converter.parseMultipleStrings(boundariesTextArea.getText());
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(null, e1.getMessage());
                    return;
                }

                ProgramEvaluator evaluator = new ProgramEvaluator(collection);
                System.out.println("Start");
                long start = System.currentTimeMillis();
                if (combo.getSelectedItem().equals(BRUTE_FORCE))
                {
                    BruteForceProgramIterator iterator = new BruteForceProgramIterator(evaluator);
                    iterator.iterate(registerCountSlider.getValue(), instructionCountSlider.getValue());
                }
                if (combo.getSelectedItem().equals(REVERSE_SOLUTION_SEARCH))
                {
                    ReverseProgramIterator iterator = new ReverseProgramIterator(evaluator);
                    iterator.iterate(registerCountSlider.getValue(), instructionCountSlider.getValue());
                }
                if (combo.getSelectedItem().equals(RANDOM))
                {
                    RandomProgramIterator iterator = new RandomProgramIterator(evaluator);
                    iterator.iterate(registerCountSlider.getValue(), instructionCountSlider.getValue());
                }
                long end = System.currentTimeMillis();
                System.out.println("Finished in " + (end - start) + " ms ");
            }
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

    private static InOutParameters createParameter(double a, double result)
    {
        Map<String, Double> startParameters  = new HashMap<>();
        startParameters.put("r0", a);

        Map<String, Double> endParameters = new HashMap<>(1);
        endParameters.put("r0", result);

        InOutParameters parameters = new InOutParameters();
        parameters.input = startParameters;
        parameters.expectedOutput = endParameters;
        return parameters;
    }
}
