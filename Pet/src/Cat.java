
public class Cat extends Feline{

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
}
