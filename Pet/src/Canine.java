
public class Canine extends Animal {

	public Canine(String argPict, String argFood, int argHunger, String argBoundaries, String argLocation) {
		super(argPict, argFood, argHunger, argBoundaries,argLocation);

	}

	public void roam() {
		System.out.println("Canine move in packs");
	}
}
