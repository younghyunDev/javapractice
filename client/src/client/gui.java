package client;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.*;

public class gui {
	JFrame frame=new JFrame("Chating");
	JButton button ;
	JTextArea txt= new JTextArea();
	Socket sock;
	public void createFrame() {
		try {
			sock = new Socket("127.0.0.1",5000);
			
			System.out.println(sock);
			
			
	}catch(IOException ex) {
		ex.printStackTrace();
	}	
		sendButton();
		frame.setLayout(new GridLayout(1,2));
		frame.add(txt);
		frame.add(button);
		frame.setSize(600,100);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void sendButton() {
		button = new JButton("send");
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				PrintWriter writer;
				try {
					writer = new PrintWriter(sock.getOutputStream());
					writer.println(txt.getText());
					writer.flush();
					txt.setText(null);
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				
			}
		});
	}
}
