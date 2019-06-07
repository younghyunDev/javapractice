package application;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client {
	int i=-1; // �ɽ��� ���� ����� ���� ���Դϴ�.
	Socket socket;//Client�� ������ socket�� ������ݴϴ�.
	
	public Client(Socket socket) {
		this.socket=socket;//client�� socket�� �Ҵ��մϴ�
		receive();//client�� ������ ������ ������ receive�ϰ� �˴ϴ�.
		
	}

	
	//�ɽ��̰� ���ִ� ������ ������� ��ȯ���ݴϴ�
	private String simsim() {
		if(i>=1) return "���� ���� ��\n";
		
		String[] sims= {
				"�ູ�� �Ϸ� ������!\n",
				"JAVA���θ� �ϼ���!\n",
				"�����մϴ� �� �����̿���\n",
				"ä�þ��� �� ���������?\n",
				"���� C�� �����غ�����\n",
				"������� ���μ����� ���̴� �����ϱ��?\n",
				"����� ������ ��������?\n",
				"Adam optimization�� �����غ�����\n",
				"Relu�Լ��� backpropagation�� simoid���� �� �Ͼ�� ������ �����غ�����"
		};
		i++;
		return sims[i];
	}
	//Ŭ���̾�Ʈ�κ��� �޼��� ���޹޴� �޼ҵ�
	private void receive() {
		Runnable thread= new Runnable() {
			
			@Override
			public void run() {
				try {
					while(true) {
						InputStream in = socket.getInputStream();//inputstream�� �Ҵ�޽��ϴ�.
						byte[] buffer=new byte[512];//buffer�� �������ݴϴ�.
						int length= in.read(buffer);//read�Լ��� ���� buffer���� ���ڸ� ����� ���ڿ�ũ�⸦ ��ȯ�Ͽ� length�� �����մϴ�.
						while(length==-1) throw new IOException();//�޼����� �ȵ����� ����ó�����մϴ�.
						System.out.println("[�޽��� ���� ����] "
								+ socket.getRemoteSocketAddress()
								+ ": "+ Thread.currentThread().getName());
						String message = new String(buffer,0,length,"UTF-8");
						
						if(message.equals("�͸�: �ɽ���\n"))// ���� ����ڰ� �ɽ��̶�� �Է��Ѵٸ� �ɽ��� ������ ��������ݴϴ�.
							message=simsim();//�ɽ��̿� �ִ� ���� �������ݴϴ�.
						for(Client client: Main.clients) {
							client.send(message); //������ ������ ��� ����ڵ鿡�� �������ݴϴ�.
						}
					}
				} catch (Exception e) {
					try {
						System.out.println("[�޽��� ���� ����] "
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
	
	//Ŭ���̾�Ʈ���� �޼�������
	public void send(String message) {
		Runnable thread = new Runnable() {
			
			@Override
			public void run() {
				try {
					OutputStream out =socket.getOutputStream();//outputstream�� �Ҵ�޽��ϴ�.
					byte[] buffer =message.getBytes("UTF-8");//message�� buffer�� �Ҵ�޽��ϴ�
					out.write(buffer);//buffer�� �ۼ��ϰ� flush�� ���� �����ݴϴ�.
					out.flush();
				} catch (Exception e) {
					try {
						System.out.println("[�޽��� �۽� ����]" //������ ������
								+socket.getRemoteSocketAddress()
								+": "+Thread.currentThread().getName());
						Main.clients.remove(Client.this);//clients ���� client�� �����մϴ�
						socket.close();//client�� socket�� �ݽ��ϴ�.
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
				
			}
		};
		Main.threadPool.submit(thread);
	}
	

}