
public class Animal {

	String picture = "file name";
	String food ="meat or grass";
	int hunger =0;
	float boundaries=0;
	
	public Animal(String argPict, String argFood, int argHunger, float argBoundaries ) {
		picture=argPict;
		food=argFood;
		hunger=argHunger;
		boundaries=argBoundaries;
	}
	
	public void makeNoise() {
		System.out.println("make noise");
	}
	public void eat() {
		System.out.println("eating meat or grass");
	}
	public void sleep() {
		System.out.println("Zzzzzz");
	}
	public void roam() {
		System.out.println("wandering around waiting to bump into a food source or a boundary");
	}	
}
