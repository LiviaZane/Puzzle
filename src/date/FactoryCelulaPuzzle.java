package date;

public class FactoryCelulaPuzzle extends AbstractFactory{
	
	@Override
	public CelulaPuzzle creareCelulaPuzzle() {
		
		return new CelulaPuzzle();
		
	}
}
