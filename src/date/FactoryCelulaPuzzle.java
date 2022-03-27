package date;

public class FactoryCelulaPuzzle extends AbstractFactory{		// creeaza obiecte

	@Override
	public CelulaPuzzle creareCelulaPuzzle() {
		return new CelulaPuzzle();	
	}
}


//