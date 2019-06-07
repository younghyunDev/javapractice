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
	TextField userName=new TextField();// 사용자 이름을 작성하는 텍스트창입니다.
	Socket socket;
	TextArea textArea;//메세지를 받기위한 용도입니다.
	//클라이언트 프로그램 동작 메소드입니다.
	private void startClient(String IP,int port) {
		Thread thread=new Thread() {
			public void run() {
				try {
					socket=new Socket(IP,port);// 소켓을 할당합니다.
					receive();//server에서 하는말을 듣습니다.
				} catch (Exception e) {
					if(!socket.isClosed()) {
						stopClient();
						System.out.println("[서버 접속 실패]");
						Platform.exit();
					}
				}
			}
		};
		thread.start();
	}
	
	//클라이언트 프로그램 종료 메소드입니다.
	private void stopClient() {
		try {
			if(socket!=null && !socket.isClosed()) {
				socket.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//메세지 수신메소드
	public void receive() {
			while(true) {
				try {
					InputStream in =socket.getInputStream();//inputstream을 할당받습니다.
					byte[] buffer = new byte[512];//buffer를 생성합니다.
					int length = in.read(buffer);//buffer에 문자열을 받고 길이를 int값으로 반환합니다.
					if(length==-1) throw new IOException();
					String message =new String(buffer,0,length,"UTF-8");//message를 만들어줍니다.
					Platform.runLater(()->{
						textArea.appendText(message);
					});
				} catch (Exception e) {
					stopClient();
					break;}
			}
		}
	//메세지 전송메소드
	public void send(String message) {
		Thread thread= new Thread() {
			public void run() {
				try {
					OutputStream out=socket.getOutputStream();//outputstream을 받습니다.
					byte[] buffer=message.getBytes("UTF-8");//buffer에 message로 부터 온 정보를 할당받습니다.
					out.write(buffer);//buffer를 write와 flush를 통해 내보냅니다.
					out.flush();
				} catch (Exception e) {
					stopClient();
				}
			}
		};
		thread.start();
	}
	
	//실제 프로그램을 동작시키는 메서드, gui를 구현하였습니다!
	
	@Override
	public void start(Stage primaryStage) {


		BorderPane root = new BorderPane();
		root.setPadding(new Insets(5));
		
		HBox hbox=new HBox();
		hbox.setSpacing(5);
		
		userName=new TextField(); //사용자 이름을 넣어줄 text field입니다.
		userName.setPrefWidth(150);
		userName.setPromptText("닉네임을 입력하세요");
		HBox.setHgrow(userName, Priority.ALWAYS);
		
		TextField IPText=new TextField("127.0.0.1");// IP주소를 넣어줄 text field입니다.
		TextField portText=new TextField("9876");//port번호를 넣어줄 textfield입니다.
		portText.setPrefWidth(80);
		
		hbox.getChildren().addAll(userName,IPText,portText);//hbox의 요소로 이름,ip,port번호를 받습니다.
		root.setTop(hbox); //borderPane의 가장위쪽에 놓습니다.
		
		textArea=new TextArea();//채팅창을 설정합니다.
		textArea.setBackground(new Background( new BackgroundFill( Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY ) ));
		textArea.setEditable(false);
		root.setCenter(textArea);//채팅창 가운데에 설정합니다.
		
		TextField input=new TextField();//입력창을 넣어줍니다.
		input.setFont(Font.loadFont("Maplestory Light.ttf", 15));
		input.setPrefHeight(Double.MAX_VALUE);
		input.setPrefWidth(Double.MAX_VALUE);
		input.setDisable(true);
		
		input.setOnAction(event -> {
			send(userName.getText()+": "+input.getText()+"\n");
			input.setText("");//글자를 입력받습니다.
			input.requestFocus();
		});
		
		Button sendButton =new Button("전송");//전송버튼입니다.
		sendButton.setPrefHeight(Double.MAX_VALUE);
		sendButton.setDisable(true);
		
		sendButton.setOnAction(event->{
			send(userName.getText()+": "+input.getText()+"\n");
			input.setText("");
			input.requestFocus();
		});
		
		
		Button connectionButton = new Button("Login");// Login 버튼을 통항여 다른 네트워크에 접속을 시도합니다.
		connectionButton.setPrefHeight(Double.MAX_VALUE);
		connectionButton.setOnAction(event->{
			if(connectionButton.getText().equals("Login")) {
				int port=9876; //정해진 포트번호에 접속을 시도합니다.
				try {
					port=Integer.parseInt(portText.getText()); // textfield에 있는 port값을 가져옵니다.
				} catch (Exception e) {
					e.printStackTrace();
				}
				startClient(IPText.getText(), port);
				Platform.runLater(()->{
					textArea.appendText("[채팅방 접속]\n");
				});
				connectionButton.setText("Logout"); //logout 버튼을 통해 접속 끊습니다.
				input.setDisable(false);//채팅을 할 수 있도록 바꿔줍니다.
				sendButton.setDisable(false);//버튼을 누를 수 있도록 바꿔줍니다.
				input.requestFocus();
			}else {
				stopClient();
				Platform.runLater(()->{
					textArea.appendText("[채팅방 퇴장]\n");
				});
				connectionButton.setText("Login");
				input.setDisable(true);
				sendButton.setDisable(true);
			}
		});
		
		BorderPane pane =new BorderPane(); // borderpand을 생성한후 지금까지 만든 element들을 집어 넣어줍니다.
		pane.setLeft(connectionButton);
		pane.setCenter(input);
		pane.setRight(sendButton);
		pane.setPrefHeight(80);
		root.setBottom(pane);//pane을 root에 넣어줍니다.
		
		
		Scene scene = new Scene(root,500,800);
		primaryStage.setTitle("[ 채팅 클라이언트 ]");
		primaryStage.setScene(scene);
		primaryStage.setOnCloseRequest(event -> stopClient());
		primaryStage.show();
		
		connectionButton.requestFocus();
		
	}
	//프로그램의 진입
	public static void main(String[] args) {
		launch(args);
	}
}