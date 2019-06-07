package application;
	

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Main  {
	
	//스레드 관리용  스레드 숫자를 제한한다.
	public static ExecutorService threadPool;
	//Client 인스턴스들을 관리하기 위하여 벡터를 사용하였습니다.
	public static Vector<Client> clients =new Vector<Client>();
	//서버용 소켓 만들어줍니다.
	ServerSocket serverSocket;
	
	//서버를 구동시켜서 클라이언트의 연결을 기다리는 메소드
	public void startServer(int port) {
		try {
			serverSocket = new ServerSocket(); //서버용 소켓을 할당한 후 대기를 합니다.
			serverSocket.bind(new InetSocketAddress(port)); //로컬 IP를 가지고 Port를 연 후 클라이언트 접속을 기다립니다.
			System.out.println("서버를 시작합니다.");
		} catch (Exception e) {
			if(!serverSocket.isClosed()) {
				stopServer(); //server가 아직 실행중이면 닫아줍니다.
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
						System.out.println("[Client Access]"
								+socket.getRemoteSocketAddress() //client의 주소를 가져옵니다.
								+": "+Thread.currentThread().getName()); //어떤 스레드를 이용중인지 출력!
					} catch (Exception e) {
						if(!serverSocket.isClosed()) {
							stopServer();//서버를 종료시켜줍니다.
						}
						break;
					}
				}
				
			}
		};
		threadPool=Executors.newCachedThreadPool(); //스레드풀을 만들어줍니다. 스레드풀은 서버에 많은 클라이언트들이 몰려 스레드가 증폭되는 걸 막아주기 위 해 사용합니다.
		threadPool.submit(thread); //스레드풀에게 작업처리를 요청합니다.
	}
	
	//서버를 중지시키는 메서드
	public void stopServer(){
		try {
			//현재 작동중인 모든 소켓 닫기
			Iterator<Client> iterator = clients.iterator();
			while(iterator.hasNext()) {
				Client client = iterator.next();
				client.socket.close();//client의 socket을 종료시킵니다.
				iterator.remove();//종료시킨 client는 clients에서 삭제시켜줍니다.
			}
			//서버 소켓 객체 닫기
			if(serverSocket != null && !serverSocket.isClosed()) {
				serverSocket.close();//서버를 종료시킵니다.
			}
			//쓰레드 풀 종료하기
			if(threadPool != null && !threadPool.isShutdown()) {
				threadPool.isShutdown();//스레드풀을 종료시킵니다.
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
	public void start() {
		int port= 9876;
		startServer(port);// 포트열어주기
	}
	//Main
	public static void main(String[] args) {
		Main a = new Main();
		a.start();
	}
}