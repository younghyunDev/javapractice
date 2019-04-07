package Mycode;
public class Dog extends Canine implements Pet{

	public Dog(){};
	public Dog(String argPict, String argFood, int argHunger, String argBoundaries, String argLocation) {
		super(argPict, argFood, argHunger, argBoundaries,argLocation);
		
	}
	public void makeNoise() {
		System.out.println("bow-wow (dog barking)");
	}
	public void eat() {
		System.out.println("Dog eating meats(?)");
	}
	public void takeWalk() {
		System.out.println(" Oh yes~! I like taking a walk");
	}
	public void charming(){
		System.out.println(" bow wow (shake tail)");
	}

}
