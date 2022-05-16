/**********************************************************************************************************
*    10 sabloane de proiectare utilizate : Singleton, Observer si Buider, in acest fisier,
*                  Factory, Facade, Template, Mediator si Bridge, in ActionListenerSTART,
*                  Memento la Creare/Restaurare tablaPuzzle in/din XML, in ActionListenerSALV si INCARCA,
*                  precum si Iterator, in tot programul.
***********************************************************************************************************/
package date;

import java.util.Vector;
import GUI.GUI_Aplicatie;

public class JocPuzzle {
	
	public static void main(String[] args) {
		
		int dimensiune_puzzle = 3;
		Vector<CelulaPuzzle> cp = new Vector<CelulaPuzzle>(dimensiune_puzzle * dimensiune_puzzle);
		for (int i = 0; i < dimensiune_puzzle * dimensiune_puzzle; i++) { // utilizare sablon Builder
			CelulaPuzzle celulaCurenta = new CelulaPuzzle.Builder().creazaIndice(i).creazaVal(Integer.toString(i+1)).build();
			cp.add(celulaCurenta);
		}
		
		TablaPuzzle tablaPuzzle = new TablaPuzzle();
		tablaPuzzle.setTabla(cp);                         // copiem vectorul de celule in tabla de lucru
		tablaPuzzle.setDim_puzzle(dimensiune_puzzle);
		
		GUI_Aplicatie aplicatie = GUI_Aplicatie.getInstance(tablaPuzzle);    // utilizare sablon Singleton
		tablaPuzzle.adaugaObservator(aplicatie);                             // utilizare sablon Observer

	}
	
}


