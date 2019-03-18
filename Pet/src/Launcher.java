
public class Launcher {
	public static void main(String[] args) {
		Wolf w = new Wolf();
		w.makeNoise();
		w.roam();
		w.eat();
		w.sleep();
		
		PetOwner person =new PetOwner();
		person.start();
	}

}
