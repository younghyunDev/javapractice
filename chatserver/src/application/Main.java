package application;
	
import javafx.*;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.Vector;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;


public class Main extends Application {
	
	//스레드 관리용  스레드 숫자를 제한한다.
	public static ExecutorService threadPool;
	public static Vector<Client> clients =new Vector<Client>();
	
	ServerSocket serverSocket;
	
	//서버를 구동시켜서 클라이언트의 연결을 기다리는 메소드
	public void startServer(String IP,int port) {
		try {
			serverSocket = new ServerSocket();
			serverSocket.bind(new InetSocketAddress(IP,port));
			System.out.println("서버를 시작합니다.");
		} catch (Exception e) {
			if(!serverSocket.isClosed()) {
				stopServer();
			}
			return;
		}
		//클라이언트가 접속할 때 까지 계속 기달리는 쓰레드
		Runnable thread= new Runnable() {
			
			@Override
			public void run() {
				while(true) {
					try {
						Socket socket=serverSocket.accept();
						clients.add(new Client(socket));
						System.out.println("[클라이엉트 접속]"
								+socket.getRemoteSocketAddress()
								+": "+Thread.currentThread().getName());
					} catch (Exception e) {
						if(!serverSocket.isClosed()) {
							stopServer();
						}
						break;
					}
				}
				
			}
		};
		threadPool=Executors.newCachedThreadPool();
		threadPool.submit(thread);
	}
	
	//서버를 중지시키는 메서드
	public void stopServer(){
		try {
			//현재 작동중인 모든 소켓 닫기
			Iterator<Client> iterator = clients.iterator();
			while(iterator.hasNext()) {
				Client client = iterator.next();
				client.socket.close();
				iterator.remove();
			}
			//서버 소켓 객체 닫기
			if(serverSocket != null && !serverSocket.isClosed()) {
				serverSocket.close();
			}
			//쓰레드 풀 종료하기
			if(threadPool != null && !threadPool.isShutdown()) {
				threadPool.isShutdown();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	//UI생성하고,프로그램동장
	//@Override
	/*public void start(Stage primaryStage) {
		BorderPane root=new BorderPane();
		root.setPadding(new Insets(5));
		
		TextArea textArea=new TextArea();
		textArea.setEditable(false);
		textArea.setFont(new Font("나눔고딕",15));
		root.setCenter(textArea);
		
		Button toggleButton=new Button("시작하기");
		toggleButton.setMaxWidth(Double.MAX_VALUE);
		BorderPane.setMargin(toggleButton, new Insets(1,0,0,0));
		root.setBottom(toggleButton);
		
		String IP="127.0.0.1";
		int port= 9876;
		toggleButton.setOnAction(event ->{
			if(toggleButton.getText().equals("시작하기")) {
				st
				Platform.runLater(()->{
					String message=String.format("[서버 시작]\n", IP,port);
					textArea.appendText(message);
					toggleButton.setText("종료하기");
				});
			}else {
				stopServer();
				Platform.runLater(()->{
					String message=String.format("[서버 종료]\n", IP,port);
					textArea.appendText(message);
					toggleButton.setText("시작하기");
				});
			}
		});
		Scene scene=new Scene(root,400,400);
		primaryStage.setTitle("[채팅 서버]");
		primaryStage.setOnCloseRequest(event-> stopServer());
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	*/
	
	@Override
	public void start(Stage primaryStage) {
		String IP="127.0.0.1";
		int port= 9876;
		startServer(IP, port);
	}
	//프로그램의 진입점
	public static void main(String[] args) {
		launch(args);
	}
}
