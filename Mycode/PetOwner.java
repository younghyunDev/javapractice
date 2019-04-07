package Mycode;
public class PetOwner  {
	

	String ownerName;
	Pet pet;
	PetOwner(String name){
		ownerName=name;
	}
	public void talking() {
		System.out.println("Owner:hello");
		pet.charming();
		this.expUp();
	}
	public void takeAnimal(int catNum ){
		
		switch(catNum){
			case 1:
				this.pet = new Dog();
				break;
			case 2:
				this.pet = new Cat();
				break;
			case 3:
				this.pet = new Robodog();
				break;
			default:
				System.out.println("Please input number 1: Dog 2: Cat 3: Robot Dog");
		}
		


	}
	public void takeWalk(){
		pet.takeWalk();
		if(pet instanceof Dog){
			this.expUp();
		}else{
		this.expDown();
		}
	}

	public void takeHospital() {
		Vet v=new Vet();
		System.out.println("take a animal to hospital");
		v.giveShot(pet);
		if(pet instanceof Robodog){
			this.expDown();
		}else{
		this.expUp();
		}
	}

	public void hitPet(){
		System.out.println("oops!...");
		this.expDown();
	}
	public void expUp(){
		if(this.pet instanceof Robot){
			((Robodog)pet).exp++;
			if(((Robodog)pet).exp==((Robodog)pet).learningLevel){
				((Robodog)pet).learningLevel++;
				((Robodog)pet).exp=0;
				System.out.println("Level up, Level:"+((Robodog)pet).learningLevel);
	
			}
		}
		else{
			((Animal)pet).exp++;
			if(((Animal)pet).exp==((Animal)pet).intimacyLevel){
				((Animal)pet).intimacyLevel++;
				((Animal)pet).exp=0;
				System.out.println("Level up!, Level:"+((Animal)pet).intimacyLevel);
				
			}
		}
	}

	public void expDown(){
		if(this.pet instanceof Robot){
			((Robodog)pet).exp--;
			if(((Robodog)pet).exp<0){
				((Robodog)pet).learningLevel--;
				((Robodog)pet).exp=0;
				System.out.println("level down, Level:"+((Robodog)pet).learningLevel);
				
			}
		}
		else{
			((Animal)pet).exp--;
			if(((Animal)pet).exp<0){
				((Animal)pet).intimacyLevel--;
				((Animal)pet).exp=0;
				System.out.println("level down, Level:"+((Animal)pet).intimacyLevel);
				
				
			}
		}
	}

}
