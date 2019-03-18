
public class Launcher {
	public static void main(String[] args) {
		Animal objectAnimal=new Animal("animal","meat or grass",50,(float)97.7);
		Canine objectCanine=new Canine("Canine","meat",50,(float)100);
		Cat objectCat =new Cat("Cat","fish",30,(float)55);
		Dog objectDog=new Dog("Dog","meat",70,(float)77);
		Feline objectFeline=new Feline("Feline","meat",77,(float)66);
		Hippo objectHippo =new Hippo("Hippo","grass",22,(float)20);
		Lion objectLion=new Lion("Lion","meat",100,(float)100);
		Tiger objectTiger=new Tiger("Tiger","meat",100,(float)80);
		Wolf objectWolf = new Wolf("Wolf","meat",70,(float)70);
		
		System.out.println("--------------------animal's Variables--------------------");
		System.out.println("aniaml pciture : "+objectAnimal.picture);
		System.out.println("animal's food : "+objectAnimal.food);
		System.out.println("animal's hunger : "+objectAnimal.hunger);
		System.out.println("animal's boundaries : "+objectAnimal.boundaries);
		
		System.out.println("--------------------wolf's behavior--------------------");
		objectWolf.makeNoise();
		objectWolf.roam();
		objectWolf.eat();
		objectWolf.sleep();
		
		System.out.println("--------------------animal's behavior--------------------");
		objectAnimal.makeNoise();
		objectAnimal.roam();
		objectAnimal.eat();
		objectAnimal.sleep();
		
		System.out.println("--------------------Canine's behavior--------------------");
		objectCanine.makeNoise();
		objectCanine.roam();
		objectCanine.eat();
		objectCanine.sleep();
		
		System.out.println("--------------------Cat's behavior--------------------");
		objectCat.makeNoise();
		objectCat.roam();
		objectCat.eat();
		objectCat.sleep();
		
		System.out.println("--------------------Dog's behavior--------------------");
		objectDog.makeNoise();
		objectDog.roam();
		objectDog.eat();
		objectDog.sleep();
		
		System.out.println("--------------------Feline's behavior--------------------");
		objectFeline.makeNoise();
		objectFeline.roam();
		objectFeline.eat();
		objectFeline.sleep();
		
		System.out.println("--------------------Hippo's behavior--------------------");
		objectHippo.makeNoise();
		objectHippo.roam();
		objectHippo.eat();
		objectHippo.sleep();
		
		System.out.println("--------------------Lion's behavior--------------------");
		objectLion.makeNoise();
		objectLion.roam();
		objectLion.eat();
		objectLion.sleep();
		
		System.out.println("--------------------Tiger's behavior--------------------");
		objectTiger.makeNoise();
		objectTiger.roam();
		objectTiger.eat();
		objectTiger.sleep();
		
	}

}
