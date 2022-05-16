package date;

public class TemplateConcretCelula_puzzle extends TemplateCelula {
	
	CelulaPuzzle celula_sterge;

	public TemplateConcretCelula_puzzle() {
		celula_sterge = new CelulaPuzzle();
	}

	@Override
	public void setIndice(int indice) {
		celula_sterge.setIndice(indice);
	}

	@Override
	public void setVal(String val) {
		celula_sterge.setVal(val);
	}

	@Override
	public CelulaPuzzle getCelula() {
		return celula_sterge;
	}

}
