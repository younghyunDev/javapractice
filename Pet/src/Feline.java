
public abstract class Feline extends Animal{
	public Feline(String argPict, String argFood, int argHunger, String argBoundaries,String argLocation) {
		super(argPict, argFood, argHunger, argBoundaries,argLocation);
		// TODO Auto-generated constructor stub
	}

	public void roam() {
		System.out.println("Feline's roam avoid others of their own kind");
	}

}
