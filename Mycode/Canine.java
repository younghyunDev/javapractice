package Mycode;
public abstract class Canine extends Animal {

	public Canine(){};
	public Canine(String argPict, String argFood, int argHunger, String argBoundaries, String argLocation) {
		super(argPict, argFood, argHunger, argBoundaries,argLocation);
		// TODO Auto-generated constructor stub
	}

	public void roam() {
		System.out.println("Canine move in packs");
	}
}
