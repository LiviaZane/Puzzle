package date;

public class CelulaPuzzle implements Celula {

	protected int indice;                                          // indice = lin * dim + col
	private String val;

	public CelulaPuzzle() {}                                       // constructor implicit

	public int getIndice() {
		return indice;                       // getteri si setteri ....
	}
	
	@Override                               // ... necesari pentru utilizare XMLEncoder si XMLDecoder,                                               
	public void setIndice(int in) {          // pentru salvare/restaurare obiecte in/din fisiere XML
		this.indice = in;
	}

	public String getVal() {
		return val;
	}

	@Override
	public void setVal(String val) {
		this.val = val;
	}

	
	// implementare sablon Builder
	public static class Builder {
		private int indice;
		private String val;
		
		public Builder creazaIndice(int indice) {
			this.indice = indice;
			return this;
		}
		
		public Builder creazaVal(String val) {
			this.val = val;
			return this;
		}
		
		public CelulaPuzzle build() {
			return new CelulaPuzzle(this);
		}
	}
	
	private CelulaPuzzle(Builder b) {
		this.indice =  b.indice;
		this.val = b.val;
	}
	// terminare implementare sablon Builder
	
}
