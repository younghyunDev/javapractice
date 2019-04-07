package Mycode;
public class Cat extends Feline implements Pet{

	public Cat(){};
	public Cat(String argPict, String argFood, int argHunger, String argBoundaries, String argLocation) {
		super(argPict, argFood, argHunger, argBoundaries,argLocation);
		
	}
	public void makeNoise() {
		System.out.println("meow(cat crying)");
	}
	public void eat() {
		System.out.println("cat eating fish");
	}
	public void takeWalk() {
		System.out.println("Oh no! I don't want to taking a walk");
	}
	public void charming(){
		System.out.println("nyaong~~~");
	}

}
