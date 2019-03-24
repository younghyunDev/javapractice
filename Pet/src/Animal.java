
public abstract class Animal {

	String picture = "file name";
	String food ="meat or grass";
	int hunger =0;
	String boundaries="0";
	String location="(0,0)";
	
	public Animal(String argPict, String argFood, int argHunger, String argBoundaries, String argLocation ) {
		picture=argPict;
		food=argFood;
		hunger=argHunger;
		boundaries=argBoundaries;
		location = argLocation;
	}
	public void sleep() {
		System.out.println("Zzzzz");
	}

	abstract public void makeNoise();
	abstract public void eat();
	abstract public void roam();
	
}
