package Mycode;
public class Vet {
	public void giveShot(Animal a) {
		System.out.println("vet give an injection");
		a.makeNoise();
	}
	public void giveShot(Pet a) {
		System.out.println("vet give an injection");
		if(a instanceof Dog){
			((Dog)a).makeNoise();
		}
		if(a instanceof Cat){
			((Cat)a).makeNoise();
		}
		if(a instanceof Robodog){
			((Robodog)a).makeNoise();
		}
	}

}
