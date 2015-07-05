/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package generationalnumbers;

import java.awt.EventQueue;
import java.awt.TextField;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

/**
 *
 * @author Lieven
 */
public class ProgramFrame extends JFrame {

    public ProgramFrame() {

        initUI();
    }

    private void initUI() {

        final ProgramDrawerPanel surface = new ProgramDrawerPanel();
        add(surface);
        setTitle("Points");
        setSize(350, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            ProgramFrame ex = new ProgramFrame();
            ex.setVisible(true);
        });
    }
}    

