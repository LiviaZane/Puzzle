/**********************************************************************************************************
*    8 sabloane utilizate : Observer, Factory, Singleton, Facade si Template, in acest fisier
*                  Builder in ActionListenerSTART
*                  Memento la Creare/Restaurare tablaPuzzle in/din XML, in ActionListenerSALV si INCARCA
*                  si Iterator in tot programul
***********************************************************************************************************/
package date;

import java.util.Vector;
import GUI.GUI_Aplicatie;

public class JocPuzzle {
	
	public static void main(String[] args) {
		
		int dimensiune_puzzle = 3;
		Vector<CelulaPuzzle> cp = new Vector<CelulaPuzzle>(dimensiune_puzzle * dimensiune_puzzle);
		for (int i = 0; i < dimensiune_puzzle * dimensiune_puzzle; i++) {
			if (i%2 == 0) {                                                 // pentru i par, utilizez
				Facade facade = new Facade();                                           // sablonul Facade
				CelulaPuzzle celulaCurenta = facade.makeCelulaPuzzle(i);
				cp.add(celulaCurenta);
			} else if (i%3 == 0) {                                  // pentru i divizibil cu 3, utilizez
				TemplateCelula celulaTemplate = new TemplateConcretCelula_puzzle();   // sablonul Template
				celulaTemplate.setIndice(i);
				celulaTemplate.setVal(Integer.toString(i+1));
				cp.add(celulaTemplate.getCelula());
			} else {                           // pentru alte valori ale lui i (inclusiv impar), utilizez 
				FactoryCelulaPuzzle factory = new FactoryCelulaPuzzle();               // sablonul Factory
				CelulaPuzzle celulaCurenta = factory.creareCelulaPuzzle();
				celulaCurenta.setIndice(i);
				celulaCurenta.setVal(Integer.toString(i+1));
				cp.add(celulaCurenta);
			}
		}
		
		TablaPuzzle tablaPuzzle = new TablaPuzzle();
		tablaPuzzle.setTabla(cp);                         // copiem vectorul de celule in tabla de lucru
		tablaPuzzle.setDim_puzzle(dimensiune_puzzle);
		
		GUI_Aplicatie aplicatie = GUI_Aplicatie.getInstance(tablaPuzzle);    // utilizare sablon Singleton
		tablaPuzzle.adaugaObservator(aplicatie);                             // utilizare sablon Observer

	}
	
}


