/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package generationalnumbers;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;
import laboflieven.Program;
import laboflieven.ReverseProgramIterator;
import laboflieven.runners.RegularStatementRunner;
import laboflieven.runners.StatementRunner;

/**
 *
 * @author Lieven
 */
public class ProgramDrawerPanel extends javax.swing.JPanel {

    /**
     * Creates new form ProgramDrawer
     */
    public ProgramDrawerPanel() {
        initComponents();
    }
    
    public int programNumber = 45554;

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    @Override
    public void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        draw(g);
    }

    private void draw(Graphics graph)
    {
        //55550
        //2550
        ReverseProgramIterator getter = new ReverseProgramIterator(programNumber);
        Program program = getter.iterate(3,3);
        StatementRunner runner = new RegularStatementRunner();
        if (program == null)
        {
            return;
        }
        int blockWidth = 2;
        int drawWidth = 500;
        int drawHeight = 500;
        
        for (int r = 0; r < 256; r++)
           {
               for (int g = 0; g < 256; g++)
                {
                    for (int b = 0; b < 256; b++)
                        {
                            int sum = r*256*256 + g*256 + b;
                            //int sum = r*g + g*b + b;
                            Map<String, Double> initialValues = new HashMap<>();
                            initialValues.put("r0", (double)r);
                            initialValues.put("r1", (double)g);
                            initialValues.put("r2", (double)b);
                            runner.execute(program, initialValues);
                            int x = (int)program.getRegisters().get(0).value % drawWidth;
                            int y = (int)program.getRegisters().get(1).value % drawHeight;
                            
                            final Color color = new Color(r,g,b);
                            graph.setColor(color);
                            graph.fillRect(x * blockWidth, y * blockWidth, blockWidth, blockWidth);
                        }
                }
           }
    }
}
