
package Mycode;


public abstract class Animal {
	int intimacyLevel=1;
	int exp=0;
	String picture = "file name";
	private String food ="meat or grass";
	int hunger =0;
	String boundaries="0";
	String location="(0,0)";
	public Animal(){};
	public Animal(String argPict, String argFood, int argHunger, String argBoundaries, String argLocation ) {
		picture=argPict;
		food=argFood;
		hunger=argHunger;
		boundaries=argBoundaries;
		location = argLocation;
	}
	
	abstract public void makeNoise();
	public void eat() {
		System.out.println("eating meat or grass");
	}
	public void sleep() {
		System.out.println("Zzzzzz");
	}
	public void roam() {
		System.out.println("wandering around waiting to bump into a food source or a boundary");
	}


	public String getFood() {
		return food;
	}

	
	public void setFood(String food) {
		this.food = food;
	}


}
