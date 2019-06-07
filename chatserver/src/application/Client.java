package application;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client {
	int i=-1; // 심심이 앱을 만들기 위한 것입니다.
	Socket socket;//Client가 가지는 socket을 만들어줍니다.
	
	public Client(Socket socket) {
		this.socket=socket;//client의 socket을 할당합니다
		receive();//client는 서버에 들어오는 내용을 receive하게 됩니다.
		
	}

	
	//심심이가 해주는 조언을 순서대로 반환해줍니다
	private String simsim() {
		if(i>=1) return "이제 조언 끝\n";
		
		String[] sims= {
				"행복한 하루 보내요!\n",
				"JAVA공부를 하세요!\n",
				"축하합니다 곧 종강이에요\n",
				"채팅앱은 다 만들었나요?\n",
				"이제 C언어도 공부해보세요\n",
				"스레드와 프로세스의 차이는 무엇일까요?\n",
				"데드락 개념은 무엇이죠?\n",
				"Adam optimization을 설명해보세요\n",
				"Relu함수가 backpropagation이 simoid보다 덜 일어나는 이유를 설명해보세요"
		};
		i++;
		return sims[i];
	}
	//클라이언트로부터 메세지 전달받는 메소드
	private void receive() {
		Runnable thread= new Runnable() {
			
			@Override
			public void run() {
				try {
					while(true) {
						InputStream in = socket.getInputStream();//inputstream을 할당받습니다.
						byte[] buffer=new byte[512];//buffer를 생성해줍니다.
						int length= in.read(buffer);//read함수를 통해 buffer에는 글자를 남기고 문자열크기를 반환하여 length에 저장합니다.
						while(length==-1) throw new IOException();//메세지가 안들어오면 예외처리를합니다.
						System.out.println("[메시지 수신 성공] "
								+ socket.getRemoteSocketAddress()
								+ ": "+ Thread.currentThread().getName());
						String message = new String(buffer,0,length,"UTF-8");
						
						if(message.equals("익명: 심심이\n"))// 만약 사용자가 심심이라고 입력한다면 심심이 어플을 실행시켜줍니다.
							message=simsim();//심심이에 있는 말을 내보내줍니다.
						for(Client client: Main.clients) {
							client.send(message); //서버에 접속한 모든 사용자들에게 전달해줍니다.
						}
					}
				} catch (Exception e) {
					try {
						System.out.println("[메시지 수신 오류] "
								+ socket.getRemoteSocketAddress()
								+": "+ Thread.currentThread().getName());
						Main.clients.remove(Client.this);
						socket.close();
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
					
				
			}
		};
		Main.threadPool.submit(thread);
	}
	
	//클라이언트에게 메세지전송
	public void send(String message) {
		Runnable thread = new Runnable() {
			
			@Override
			public void run() {
				try {
					OutputStream out =socket.getOutputStream();//outputstream을 할당받습니다.
					byte[] buffer =message.getBytes("UTF-8");//message를 buffer에 할당받습니다
					out.write(buffer);//buffer를 작성하고 flush를 통해 끝내줍니다.
					out.flush();
				} catch (Exception e) {
					try {
						System.out.println("[메시지 송신 오류]" //오류가 났을때
								+socket.getRemoteSocketAddress()
								+": "+Thread.currentThread().getName());
						Main.clients.remove(Client.this);//clients 에서 client를 제거합니다
						socket.close();//client의 socket을 닫습니다.
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
				
			}
		};
		Main.threadPool.submit(thread);
	}
	

}