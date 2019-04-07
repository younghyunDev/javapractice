package Mycode;
public class Robodog extends Robot implements Pet{
	public Robodog(){};
	public Robodog(int price, String name) {
		super(price, name);
	}

	@Override
	public void takeWalk() {
		System.out.println("I'm 기계!! I don't want to taking a walk");
		
	}
	public void machineBark() {
		System.out.println("mung mung (기계음)");
	}
	public void charming(){
		System.out.println("사랑합니다. 주인님!");
	}
	public void makeNoise(){
		System.out.println("I'm robot, 삐이이이이이익!!!");
	}
	

}
