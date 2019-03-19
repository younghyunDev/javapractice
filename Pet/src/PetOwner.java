
public class PetOwner  {
	


	Dog d=new Dog("dog","meat",60,"350*200","(354,123)");
	
	public void talking() {
		System.out.println("hello");
	}
	
	public void takeHospital() {
		Vet v=new Vet();
		System.out.println("take a animal to hospital");
		v.giveShot(d);
	}


}
