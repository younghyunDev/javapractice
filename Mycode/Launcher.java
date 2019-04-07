package Mycode;

import java.util.Scanner;
public class Launcher {
	public static void main(String[] args) {
		/*Animal[] animals =new Animal[10];
		
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
		System.out.println("package!");
		*/

		int catNum;
		System.out.println("################### tkae a pet!#####################");
		String ownerName;
		Scanner scan = new Scanner(System.in);
		System.out.println("input");
		PetOwner owner = new PetOwner(scan.nextLine());
		System.out.println("펫 분양하기");
		System.out.println("분양하고 싶으신 종류를 입력해주세요: 1.Dog , 2. Cat , 3. RobotDog");
		catNum=scan.nextInt();
		owner.takeAnimal(catNum);
	

		while(true){
			System.out.println("하고 싶은 행동을 골라주세요!");
			System.out.println("1. Pet과 대화하기");
			System.out.println("2. 산책시키기");
			System.out.println("3. 동물병원 데려가기");
			System.out.println("4. 동물 때리기...");
			int actNum= scan.nextInt();
			switch(actNum){
				case 1:
					owner.talking();
					break;
				case 2:
					owner.takeWalk();
					break;
				case 3:
					owner.takeHospital();
					break;
				case 4:
					owner.hitPet();
					break;
				default:
					System.out.println("다시 입력해주세요");
			}
		}
		

	}

}
