package application;
	

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Main  {
	
	//������ ������  ������ ���ڸ� �����Ѵ�.
	public static ExecutorService threadPool;
	//Client �ν��Ͻ����� �����ϱ� ���Ͽ� ���͸� ����Ͽ����ϴ�.
	public static Vector<Client> clients =new Vector<Client>();
	//������ ���� ������ݴϴ�.
	ServerSocket serverSocket;
	
	//������ �������Ѽ� Ŭ���̾�Ʈ�� ������ ��ٸ��� �޼ҵ�
	public void startServer(int port) {
		try {
			serverSocket = new ServerSocket(); //������ ������ �Ҵ��� �� ��⸦ �մϴ�.
			serverSocket.bind(new InetSocketAddress(port)); //���� IP�� ������ Port�� �� �� Ŭ���̾�Ʈ ������ ��ٸ��ϴ�.
			System.out.println("������ �����մϴ�.");
		} catch (Exception e) {
			if(!serverSocket.isClosed()) {
				stopServer(); //server�� ���� �������̸� �ݾ��ݴϴ�.
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
						System.out.println("[Client Access]"
								+socket.getRemoteSocketAddress() //client�� �ּҸ� �����ɴϴ�.
								+": "+Thread.currentThread().getName()); //� �����带 �̿������� ���!
					} catch (Exception e) {
						if(!serverSocket.isClosed()) {
							stopServer();//������ ��������ݴϴ�.
						}
						break;
					}
				}
				
			}
		};
		threadPool=Executors.newCachedThreadPool(); //������Ǯ�� ������ݴϴ�. ������Ǯ�� ������ ���� Ŭ���̾�Ʈ���� ���� �����尡 �����Ǵ� �� �����ֱ� �� �� ����մϴ�.
		threadPool.submit(thread); //������Ǯ���� �۾�ó���� ��û�մϴ�.
	}
	
	//������ ������Ű�� �޼���
	public void stopServer(){
		try {
			//���� �۵����� ��� ���� �ݱ�
			Iterator<Client> iterator = clients.iterator();
			while(iterator.hasNext()) {
				Client client = iterator.next();
				client.socket.close();//client�� socket�� �����ŵ�ϴ�.
				iterator.remove();//�����Ų client�� clients���� ���������ݴϴ�.
			}
			//���� ���� ��ü �ݱ�
			if(serverSocket != null && !serverSocket.isClosed()) {
				serverSocket.close();//������ �����ŵ�ϴ�.
			}
			//������ Ǯ �����ϱ�
			if(threadPool != null && !threadPool.isShutdown()) {
				threadPool.isShutdown();//������Ǯ�� �����ŵ�ϴ�.
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
	public void start() {
		int port= 9876;
		startServer(port);// ��Ʈ�����ֱ�
	}
	//Main
	public static void main(String[] args) {
		Main a = new Main();
		a.start();
	}
}