package date;

public class TemplateConcretCelula extends TemplateCelula { // se foloseste doar TemplateConcrCel_sterge

	int indice;
	String val;
	
	public TemplateConcretCelula() {}

	@Override
	public void setIndice(int indice) {
		this.indice = indice;
	}

	@Override
	public void setVal(String val) {
		this.val = val;
	}

	@Override
	public CelulaPuzzle getCelula() {
		// TODO Auto-generated method stub
		return null;
	}

}
