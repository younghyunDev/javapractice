package mycode2;
import java.awt.*;
import javax.swing.JButton;
import javax.swing.JFrame;

public class ButtonThree extends JFrame
{
    JButton b1=new JButton("1");
    JButton b2 =new JButton("2");
    JButton b3 = new JButton("3");
    
    public ButtonThree()
    {
        super("Subject here");

        this.setLayout(new FlowLayout());

        this.add(b1);
        this.add(b2);
        this.a3dd(b3);
        
        
        this.setSize(300,400);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
