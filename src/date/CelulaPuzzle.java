package date;



public class CelulaPuzzle {

	private int indice;                                                               // indice = lin * dim + col
	private int val;                                                                  // valoarea din celula

	public CelulaPuzzle() {}                                                          // constructor implicit

	public int getIndice() {
		return indice;                                                                // getteri si setteri
	}
                                                // ... necesari pentru serializare/utilizare XMLEncoder si XMLDecoder din pachetul java.beans,
	public void setIndice(int in) {                   // pentru salvare/restaurare obiecte in/din fisiere XML
		this.indice = in;
	}

	public int getVal() {
		return val;
	}

	public void setVal(int val) {
		this.val = val;
	}

										                        // suprascrierea metodei toString, folosita pentru testare
	@Override                                                   // spre finalul aplicatiei va fi stearsa
	public String toString() {
		return "CelulaPuzzle [indice=" + indice + ", val=" + val + "]";
	}

}
