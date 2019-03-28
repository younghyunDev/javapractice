
public class Launcher {
	public static void main(String[] args) {
		Animal[] animals =new Animal[10];
		/*
		animals[0]=new Dog("dog","meat",60,"350*200","(354,123)");
		animals[1]=new Wolf("Wolf","meat",80,"650*400","(843,623)");
		animals[2]=new Hippo("Hippo","grass",30,"550*300","(534,923)");
		animals[3]=new Cat("Cat","fish?",40,"280*200","(354,323)");
		animals[4]=new Tiger("Tiger","meat",90,"990*700","(734,623)");
		animals[5]=new Lion("Lion","meat",90,"1000*800","(743,353)");
		
		
		for(int i=0;i<6;i++) {
			System.out.println("-----------------"+animals[i].picture+" Variables-----------------");
			System.out.println(animals[i].picture + " pciture : "+animals[i].picture);
			System.out.println(animals[i].picture +" foodd : "+animals[i].food);
			System.out.println(animals[i].picture + "hunger : "+animals[i].hunger);
			System.out.println(animals[i].picture +" boundaries : "+animals[i].boundaries);
			System.out.println(animals[i].picture +" Location : "+animals[i].location);
		}
		
		
		for(int i=0;i<6;i++) {
		System.out.println("--------------------"+animals[i].picture+" behavior--------------------");
		animals[i].makeNoise();
		animals[i].roam();
		animals[i].eat();
		animals[i].sleep();
		}
		*/

		Animal dog =new Dog();
		dog.setFood("meat");
		dog=new Cat();
		dog.setFood("fish");
		dog.getFood();
		

		
		
	}

}
