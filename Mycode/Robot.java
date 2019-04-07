package Mycode;
public class Robot {
	public Robot(){};
	public Robot(int price, String name) {
		buyingPrice=price;
		robotName=name;
	}
	int learningLevel=1;
	int exp=0;
	int buyingPrice; // 
	String robotName; 
	
	public void introduce() {
		System.out.println("I'm "+robotName);
	}
}
