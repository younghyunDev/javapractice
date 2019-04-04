package Mycode2;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
public class Launcher implements ActionListener {
    private static String labelPrefix="number of button clicks :";
    private int numClicks=0;
    JLabel label =new JLabel(labelPrefix+ "0    ");
    public void go(String title){
        JFrame frame= new JFrame(title);
        JButton button=new JButton("Swing button!");
        button.addActionListener(this);

        JPanel panel = new JPanel();
        panel.add(button);
        panel.add(label);
        frame.getContentPane().add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent event){
        label.setText(labelPrefix+ ++numClicks);
    }
    public static void main(String[] args){
        Launcher button =new Launcher();
        button.go("Event handling");
    }
}