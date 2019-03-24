
public class Hippo extends Animal {
	public Hippo(String argPict, String argFood, int argHunger, String argBoundaries, String argLocation) {
		super(argPict, argFood, argHunger, argBoundaries,argLocation);
		// TODO Auto-generated constructor stub
	}
	
	public void makeNoise() {
		System.out.println("hippo crying");
	}
	public void eat() {
		System.out.println("hippo eating grass");
	}
	public void roam(){
		System.out.println("roaming");
	}
	

}
