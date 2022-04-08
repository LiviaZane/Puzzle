package date;

public class Facade {
	private Celula celula1;    // interfata Celula, implementatata de CelulaPuzzle si CelulaPuzzle_sterge
	private Celula celula2;
	
	public Facade() {
		celula1 = new CelulaPuzzle();
		celula2 = new CelulaPuzzle_sterge();
	}
	
	public CelulaPuzzle makeCelulaPuzzle(int i) {
		celula1.setVal(Integer.toString(i+1));                  // ascundere complexitate
		celula1.setIndice(i);
		return (CelulaPuzzle) celula1;
	}
	
	public CelulaPuzzle makeCelulaPuzzle_sterge(int i) {
		celula2.setVal(Integer.toString(i+1));                  // ascundere complexitate
		celula2.setIndice(i);
		return (CelulaPuzzle_sterge) celula2;
	}
}
