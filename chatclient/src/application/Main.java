package application;
	
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import java.io.OutputStream;

import javafx.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;


public class Main extends Application {
	TextField userName=new TextField();// ����� �̸��� �ۼ��ϴ� �ؽ�Ʈâ�Դϴ�.
	Socket socket;
	TextArea textArea;//�޼����� �ޱ����� �뵵�Դϴ�.
	//Ŭ���̾�Ʈ ���α׷� ���� �޼ҵ��Դϴ�.
	private void startClient(String IP,int port) {
		Thread thread=new Thread() {
			public void run() {
				try {
					socket=new Socket(IP,port);// ������ �Ҵ��մϴ�.
					receive();//server���� �ϴ¸��� ����ϴ�.
				} catch (Exception e) {
					if(!socket.isClosed()) {
						stopClient();
						System.out.println("[���� ���� ����]");
						Platform.exit();
					}
				}
			}
		};
		thread.start();
	}
	
	//Ŭ���̾�Ʈ ���α׷� ���� �޼ҵ��Դϴ�.
	private void stopClient() {
		try {
			if(socket!=null && !socket.isClosed()) {
				socket.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//�޼��� ���Ÿ޼ҵ�
	public void receive() {
			while(true) {
				try {
					InputStream in =socket.getInputStream();//inputstream�� �Ҵ�޽��ϴ�.
					byte[] buffer = new byte[512];//buffer�� �����մϴ�.
					int length = in.read(buffer);//buffer�� ���ڿ��� �ް� ���̸� int������ ��ȯ�մϴ�.
					if(length==-1) throw new IOException();
					String message =new String(buffer,0,length,"UTF-8");//message�� ������ݴϴ�.
					Platform.runLater(()->{
						textArea.appendText(message);
					});
				} catch (Exception e) {
					stopClient();
					break;}
			}
		}
	//�޼��� ���۸޼ҵ�
	public void send(String message) {
		Thread thread= new Thread() {
			public void run() {
				try {
					OutputStream out=socket.getOutputStream();//outputstream�� �޽��ϴ�.
					byte[] buffer=message.getBytes("UTF-8");//buffer�� message�� ���� �� ������ �Ҵ�޽��ϴ�.
					out.write(buffer);//buffer�� write�� flush�� ���� �������ϴ�.
					out.flush();
				} catch (Exception e) {
					stopClient();
				}
			}
		};
		thread.start();
	}
	
	//���� ���α׷��� ���۽�Ű�� �޼���, gui�� �����Ͽ����ϴ�!
	
	@Override
	public void start(Stage primaryStage) {


		BorderPane root = new BorderPane();
		root.setPadding(new Insets(5));
		
		HBox hbox=new HBox();
		hbox.setSpacing(5);
		
		userName=new TextField(); //����� �̸��� �־��� text field�Դϴ�.
		userName.setPrefWidth(150);
		userName.setPromptText("�г����� �Է��ϼ���");
		HBox.setHgrow(userName, Priority.ALWAYS);
		
		TextField IPText=new TextField("127.0.0.1");// IP�ּҸ� �־��� text field�Դϴ�.
		TextField portText=new TextField("9876");//port��ȣ�� �־��� textfield�Դϴ�.
		portText.setPrefWidth(80);
		
		hbox.getChildren().addAll(userName,IPText,portText);//hbox�� ��ҷ� �̸�,ip,port��ȣ�� �޽��ϴ�.
		root.setTop(hbox); //borderPane�� �������ʿ� �����ϴ�.
		
		textArea=new TextArea();//ä��â�� �����մϴ�.
		textArea.setBackground(new Background( new BackgroundFill( Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY ) ));
		textArea.setEditable(false);
		root.setCenter(textArea);//ä��â ����� �����մϴ�.
		
		TextField input=new TextField();//�Է�â�� �־��ݴϴ�.
		input.setFont(Font.loadFont("Maplestory Light.ttf", 15));
		input.setPrefHeight(Double.MAX_VALUE);
		input.setPrefWidth(Double.MAX_VALUE);
		input.setDisable(true);
		
		input.setOnAction(event -> {
			send(userName.getText()+": "+input.getText()+"\n");
			input.setText("");//���ڸ� �Է¹޽��ϴ�.
			input.requestFocus();
		});
		
		Button sendButton =new Button("����");//���۹�ư�Դϴ�.
		sendButton.setPrefHeight(Double.MAX_VALUE);
		sendButton.setDisable(true);
		
		sendButton.setOnAction(event->{
			send(userName.getText()+": "+input.getText()+"\n");
			input.setText("");
			input.requestFocus();
		});
		
		
		Button connectionButton = new Button("Login");// Login ��ư�� ���׿� �ٸ� ��Ʈ��ũ�� ������ �õ��մϴ�.
		connectionButton.setPrefHeight(Double.MAX_VALUE);
		connectionButton.setOnAction(event->{
			if(connectionButton.getText().equals("Login")) {
				int port=9876; //������ ��Ʈ��ȣ�� ������ �õ��մϴ�.
				try {
					port=Integer.parseInt(portText.getText()); // textfield�� �ִ� port���� �����ɴϴ�.
				} catch (Exception e) {
					e.printStackTrace();
				}
				startClient(IPText.getText(), port);
				Platform.runLater(()->{
					textArea.appendText("[ä�ù� ����]\n");
				});
				connectionButton.setText("Logout"); //logout ��ư�� ���� ���� �����ϴ�.
				input.setDisable(false);//ä���� �� �� �ֵ��� �ٲ��ݴϴ�.
				sendButton.setDisable(false);//��ư�� ���� �� �ֵ��� �ٲ��ݴϴ�.
				input.requestFocus();
			}else {
				stopClient();
				Platform.runLater(()->{
					textArea.appendText("[ä�ù� ����]\n");
				});
				connectionButton.setText("Login");
				input.setDisable(true);
				sendButton.setDisable(true);
			}
		});
		
		BorderPane pane =new BorderPane(); // borderpand�� �������� ���ݱ��� ���� element���� ���� �־��ݴϴ�.
		pane.setLeft(connectionButton);
		pane.setCenter(input);
		pane.setRight(sendButton);
		pane.setPrefHeight(80);
		root.setBottom(pane);//pane�� root�� �־��ݴϴ�.
		
		
		Scene scene = new Scene(root,500,800);
		primaryStage.setTitle("[ ä�� Ŭ���̾�Ʈ ]");
		primaryStage.setScene(scene);
		primaryStage.setOnCloseRequest(event -> stopClient());
		primaryStage.show();
		
		connectionButton.requestFocus();
		
	}
	//���α׷��� ����
	public static void main(String[] args) {
		launch(args);
	}
}