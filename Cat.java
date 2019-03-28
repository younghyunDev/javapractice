
public class Cat extends Feline implements Pet{

	public Cat(String argPict, String argFood, int argHunger, String argBoundaries, String argLocation) {
		super(argPict, argFood, argHunger, argBoundaries,argLocation);
		// TODO Auto-generated constructor stub
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
}
