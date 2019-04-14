package mycode2;
import java.awt.*;
public class FramePractice
{
    Frame frame =new Frame("frame");
    Button button = new Button("button");

    public void createFrame()
    {
        frame.add(button);
        frame.setSize(300,600);
        frame.setVisible(true);
    }

}