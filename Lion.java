
public class Lion extends Feline{
	public Lion(String argPict, String argFood, int argHunger, String argBoundaries, String argLocation) {
		super(argPict, argFood, argHunger, argBoundaries,argLocation);
		// TODO Auto-generated constructor stub
	}
	public void makeNoise() {
		System.out.println("Lion crying roar");
	}
	public void eat() {
		System.out.println("Lion eat meat");
	}

}
