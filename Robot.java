
public class Robot {
	public Robot(int price, String name) {
		buyingPrice=price;
		robotName=name;
	}
	int buyingPrice; // ·Îº¿ °¡°Ý
	String robotName; 
	
	public void introduce() {
		System.out.println("I'm "+robotName);
	}
}
