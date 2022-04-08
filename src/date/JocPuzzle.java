package date;        // 6 sabloane utilizate : Singleton, Observer, Facade si Factory in acest fisier
                                         // Builder in ActionListenerSTART si Iterator in tot programul

import java.util.Vector;
import GUI.GUI_Aplicatie;

public class JocPuzzle {
	
	public static void main(String[] args) {
		
		int dimensiune_puzzle = 3;
		Vector<CelulaPuzzle> cp = new Vector<CelulaPuzzle>(dimensiune_puzzle * dimensiune_puzzle);
		for (int i = 0; i < dimensiune_puzzle * dimensiune_puzzle; i++) {
			if (i%2 == 0) {                                                       // pentru i par
				Facade facade = new Facade();                               // utilizez sablonul Facade
				CelulaPuzzle celulaCurenta = facade.makeCelulaPuzzle(i);
				cp.add(celulaCurenta);
			} else {                                                             // pentru i impar
				FactoryCelulaPuzzle factory = new FactoryCelulaPuzzle();    // utilizez sablonul Factory
				CelulaPuzzle celulaCurenta = factory.creareCelulaPuzzle();
				celulaCurenta.setIndice(i);
				celulaCurenta.setVal(Integer.toString(i+1));
				cp.add(celulaCurenta);
			}
		}
		
		TablaPuzzle tablaPuzzle = new TablaPuzzle();
		tablaPuzzle.setTabla(cp);                         // copiem vectorul de celule in tabla de lucru
		tablaPuzzle.setDim_puzzle(dimensiune_puzzle);
		
		GUI_Aplicatie aplicatie = GUI_Aplicatie.getInstance(tablaPuzzle);  // utilizare sablon Singleton
		tablaPuzzle.adaugaObservator(aplicatie);                           // utilizare sablon Observer

	}
	
}


