
public class Wolf extends Canine{
	public Wolf(String argPict, String argFood, int argHunger, String argBoundaries, String argLocation) {
		super(argPict, argFood, argHunger, argBoundaries,argLocation);
		// TODO Auto-generated constructor stub
	}
	public void makeNoise() {
		System.out.println("wolf crying woof");
	}
	public void eat() {
		System.out.println("wolf eating meat");
	}

}
