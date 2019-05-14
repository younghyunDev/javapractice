import java.beans.Transient;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Launcher implements Serializable{
	transient Cat test3 = new Cat("Cat","fish?",40,"280*200","(354,323)");
	public static void main(String[] args) throws FileNotFoundException, IOException {
		Animal[] animals =new Animal[10];
		
		animals[0]=new Dog("dog","meat",60,"3503*200","(354,123)");
		animals[1]=new Wolf("Wolf","meat",80,"650*400","(843,623)");
		animals[2]=new Hippo("Hippo","grass",30,"550*300","(534,923)");
		animals[3]=new Cat("Cat","fish?",40,"280*200","(354,323)");
		animals[4]=new Tiger("Tiger","meat",90,"990*700","(734,623)");
		animals[5]=new Lion("Lion","meat",90,"1000*800","(743,353)");
		
		
		for(int i=0;i<6;i++) {
			System.out.println("-----------------"+animals[i].picture+" Variables-----------------");
			System.out.println(animals[i].picture + " pciture : "+animals[i].picture);
			System.out.println(animals[i].picture +" food : "+animals[i].getFood());
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
		System.out.println("#################### 상속받은 곳에 있는지 확인하기#####################");
		animals[1].setFood("meat");
		animals[2].setFood("fish");
		System.out.println(animals[1].getFood());
		System.out.println(animals[2].getFood());
		

		

		
		System.out.println("###########################Pet and Robot###############################");
		
	    Robot aiDog=new Robodog(10000,"해피");
	    Robot aiPerson=new agent(100000000,"김비서");
	    
	    Animal realDog=new Dog("dog","meat",60,"3503*200","(354,123)");
	    Animal realCat=new Cat("Cat","fish?",40,"280*200","(354,323)");
	    
	    System.out.print("aiDog: ");aiDog.introduce();
	    System.out.print("aiPerson: ");aiPerson.introduce();
	    
	    System.out.print("(pet)Dog: ");((Dog) realDog).takeWalk();
	    System.out.print("(pet)Cat: ");((Cat)realCat).takeWalk();
		System.out.print("(pet)aiDog: ");
		((Robodog)aiDog).takeWalk();
		Dog characterone=new Dog("dog","meat",60,"3503*200","(354,123)");
		Dog charactertwo=new Dog("dog","meat",60,"3503*200","(354,123)");
		Dog characterthree=new Dog("dog","meat",60,"3503*200","(354,123)");
		Launcher pond=new Launcher();
		System.out.println("파일입출력해보기!!!!!!!!!!!");
		
		ObjectOutputStream os=new ObjectOutputStream(new FileOutputStream("test.ser"));
		os.writeObject(characterone);
		os.writeObject(charactertwo);
		os.writeObject(pond);
		os.close();
		
	}

}
