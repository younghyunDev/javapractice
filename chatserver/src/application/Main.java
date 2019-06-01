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
	
	//������ ������  ������ ���ڸ� �����Ѵ�.
	public static ExecutorService threadPool;
	public static Vector<Client> clients =new Vector<Client>();
	
	ServerSocket serverSocket;
	
	//������ �������Ѽ� Ŭ���̾�Ʈ�� ������ ��ٸ��� �޼ҵ�
	public void startServer(String IP,int port) {
		try {
			serverSocket = new ServerSocket();
			serverSocket.bind(new InetSocketAddress(IP,port));
			System.out.println("������ �����մϴ�.");
		} catch (Exception e) {
			if(!serverSocket.isClosed()) {
				stopServer();
			}
			return;
		}
		//Ŭ���̾�Ʈ�� ������ �� ���� ��� ��޸��� ������
		Runnable thread= new Runnable() {
			
			@Override
			public void run() {
				while(true) {
					try {
						Socket socket=serverSocket.accept();
						clients.add(new Client(socket));
						System.out.println("[Ŭ���̾�Ʈ ����]"
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
	
	//������ ������Ű�� �޼���
	public void stopServer(){
		try {
			//���� �۵����� ��� ���� �ݱ�
			Iterator<Client> iterator = clients.iterator();
			while(iterator.hasNext()) {
				Client client = iterator.next();
				client.socket.close();
				iterator.remove();
			}
			//���� ���� ��ü �ݱ�
			if(serverSocket != null && !serverSocket.isClosed()) {
				serverSocket.close();
			}
			//������ Ǯ �����ϱ�
			if(threadPool != null && !threadPool.isShutdown()) {
				threadPool.isShutdown();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	//UI�����ϰ�,���α׷�����
	//@Override
	/*public void start(Stage primaryStage) {
		BorderPane root=new BorderPane();
		root.setPadding(new Insets(5));
		
		TextArea textArea=new TextArea();
		textArea.setEditable(false);
		textArea.setFont(new Font("�������",15));
		root.setCenter(textArea);
		
		Button toggleButton=new Button("�����ϱ�");
		toggleButton.setMaxWidth(Double.MAX_VALUE);
		BorderPane.setMargin(toggleButton, new Insets(1,0,0,0));
		root.setBottom(toggleButton);
		
		String IP="127.0.0.1";
		int port= 9876;
		toggleButton.setOnAction(event ->{
			if(toggleButton.getText().equals("�����ϱ�")) {
				st
				Platform.runLater(()->{
					String message=String.format("[���� ����]\n", IP,port);
					textArea.appendText(message);
					toggleButton.setText("�����ϱ�");
				});
			}else {
				stopServer();
				Platform.runLater(()->{
					String message=String.format("[���� ����]\n", IP,port);
					textArea.appendText(message);
					toggleButton.setText("�����ϱ�");
				});
			}
		});
		Scene scene=new Scene(root,400,400);
		primaryStage.setTitle("[ä�� ����]");
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
	//���α׷��� ������
	public static void main(String[] args) {
		launch(args);
	}
}
