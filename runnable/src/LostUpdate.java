
public class LostUpdate implements Runnable {

	private int balance;
	public void run() {
		for(int i=0; i<50; i++) {
			increment();
			
			
		}
	}
	synchronized void increment() {
		balance++;
		System.out.println("balance is"+balance);
	}
	public static void main(String[] args) {
		LostUpdate job =new LostUpdate();
		Thread a= new Thread(job);
		Thread b= new Thread(job);
		a.start();
		b.start();
	}
}
