package Mycode;
public class Robodog extends Robot implements Pet{
	public Robodog(){};
	public Robodog(int price, String name) {
		super(price, name);
	}

	@Override
	public void takeWalk() {
		System.out.println("I'm ���!! I don't want to taking a walk");
		
	}
	public void machineBark() {
		System.out.println("mung mung (�����)");
	}
	public void charming(){
		System.out.println("����մϴ�. ���δ�!");
	}
	public void makeNoise(){
		System.out.println("I'm robot, ��������������!!!");
	}
	

}
