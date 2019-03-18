
public class Dog extends Canine{

	public Dog(String argPict, String argFood, int argHunger, float argBoundaries) {
		super(argPict, argFood, argHunger, argBoundaries);
		// TODO Auto-generated constructor stub
	}
	public void makeNoise() {
		System.out.println("bow-wow (dog barking)");
	}
	public void eat() {
		System.out.println("Dog eating meats(?)");
	}
}
