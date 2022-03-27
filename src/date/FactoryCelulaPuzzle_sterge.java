package date;

public class FactoryCelulaPuzzle_sterge extends AbstractFactory {

	@Override
	public CelulaPuzzle creareCelulaPuzzle() {
		return new CelulaPuzzle_sterge();
	}
}
