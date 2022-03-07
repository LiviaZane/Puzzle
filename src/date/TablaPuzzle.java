package date;

import java.util.Vector;
import java.io.File;

public class TablaPuzzle {
	
	private Vector<CelulaPuzzle> tabla;                                                // vector cu date din clasa CelulaPuzzle

	
	public TablaPuzzle() {}                                                            // constructor implicit
	
	public Vector<CelulaPuzzle> getTabla() {                                           // getter si setter
		return tabla;
	}

	public void setTabla(Vector<CelulaPuzzle> tabla) {
		this.tabla = tabla;
	}
	
	
	
	//datele din celule raman neschimbate, numai locul (indicele) celulelor se schimba in vectorul tabla 
	public void MutaCelula(int indice_c) {               //c este celula selectata, care va face swap cu celula goala
		int indice_cg = CelulaGoala(indice_c);           // indicele celulei goale returnat de metoda CelulaGoala       
		if(indice_cg >= 0) {        // CelulaGoala returneaza un indice intre [0, dim*dim) sau -1 daca nu are vecin celula goala
			CelulaPuzzle tmp = tabla.elementAt(indice_cg);                 //memoram celula goala in tmp
			tabla.setElementAt(tabla.elementAt(indice_c), indice_cg);      // in cg ducem c
			tabla.setElementAt(tmp, indice_c);                             // in c ducem tmp, adica am facut swap
		} else {
			System.out.println("Nu poate fi mutata");
			System.out.println();
		}
	}
	
	
	private int CelulaGoala(int indice_c) {    // c este celula selectata...returneaza indicele celulei vecine care este goala
		int i = indice_c / JocPuzzle.dim_puzzle;                 // sau -1 daca nici un vecin nu este celula goala
		int j = indice_c % JocPuzzle.dim_puzzle;
		if(i-1 >= 0 && tabla.elementAt((i-1)*(JocPuzzle.dim_puzzle)+j).getVal() == 0) {  // celula de deasupra lui c este goala
			return (i-1)*(JocPuzzle.dim_puzzle)+j;
		} else if(i+1 < JocPuzzle.dim_puzzle && tabla.elementAt((i+1)*(JocPuzzle.dim_puzzle)+j).getVal() == 0) {      // de sub 
			return (i+1)*(JocPuzzle.dim_puzzle)+j;
		} else if(j-1 >= 0 && tabla.elementAt(i*JocPuzzle.dim_puzzle+j-1).getVal() == 0) { //celula din stanga lui c este goala
			return i*JocPuzzle.dim_puzzle+j-1;
		} else if(j+1 < JocPuzzle.dim_puzzle && tabla.elementAt(i*JocPuzzle.dim_puzzle+j+1).getVal() == 0) {     // din dreapta
			return i*JocPuzzle.dim_puzzle+j+1;
		} else {                                                                        // nici o celula vecina nu este goala
			return -1;
		}
	}

	
	
	public void AmestecareCelule() {   // se porneste de la solutie si se muta celule in locul celulei goale de dim*dim*dim ori
		int indice_vecin;                        // indice celula vecina aleatorie celulei goale
		int indice_cg = JocPuzzle.dim_puzzle * JocPuzzle.dim_puzzle - 1; //celula goala este ultima celula (initial)
		for (int i = 0; i < JocPuzzle.dim_puzzle*JocPuzzle.dim_puzzle*JocPuzzle.dim_puzzle; i++) {
			indice_vecin = VecinAleatorCelulaGoala(indice_cg);     // facem swap intre celulaGoala si un vecin aleatoriu
			CelulaPuzzle tmp = tabla.elementAt(indice_cg);                     //memoram celula goala in tmp
			tabla.setElementAt(tabla.elementAt(indice_vecin), indice_cg);      // in locul celulei goale ducem celula vecina
			tabla.setElementAt(tmp, indice_vecin);                             // in vecin ducem celula goala (tmp)
			indice_cg = indice_vecin;     // actualizam indice_cg ... devine indicele vecinului cu care a facut swap
		}
	}
	

	private int VecinAleatorCelulaGoala(int cg) {          // returneaza un vecin aleatoriu al celulei goale
		Vector<Integer> vecini = new Vector<>();               // vectorul in care stocam vecinii
		int i = cg / JocPuzzle.dim_puzzle;             // translatere indice celula goala in coordonate i si j
		int j = cg % JocPuzzle.dim_puzzle;             // pentru a putea "vedea" care celula e sus/jos/stanga/dreapta
		
		if(i-1 >= 0) {                                              // daca exista o celula deasupra celulei goale
			vecini.add((i-1)*(JocPuzzle.dim_puzzle)+j);                   // adaugam indicele ei la vectorul vecini
		} 
		if(i+1 < (int)Math.sqrt(tabla.size())) {                    // daca exista o celula sub celula goala
			vecini.add((i+1)*(JocPuzzle.dim_puzzle)+j);                   // adaugam indicele ei la vectorul vecini
		} 
		if(j-1 >= 0) {                                              // daca exista o celula in stanga celulei goale
			vecini.add(i*JocPuzzle.dim_puzzle+j-1);                       // adaugam indicele ei la vectorul vecini
		} 
		if(j+1 < JocPuzzle.dim_puzzle) {                            // daca exista o celula in dreapta celulei goale
			vecini.add(i*JocPuzzle.dim_puzzle+j+1);                       // adaugam indicele ei la vectorul vecini
		}
		
		return vecini.elementAt((int)(Math.random() * 10) % vecini.size());  // returneaza un vecin aleatoriu...random()->[0,1)
	}

	
	
	public Boolean JocFinalizat() {                         // compara indecsii tablei de lucru cu indecsii initiali (solutia)
		int contor = 1;
		if (this.tabla.lastElement().getVal() == 0) {                // daca ultimul element e zero...mergem mai departe...
			for (int i = 0; i < JocPuzzle.dim_puzzle * JocPuzzle.dim_puzzle-1; i++) {  // de la 0 la penultimul
				if (this.tabla.elementAt(i).getVal() == i+1) {
					contor++;
				}
			}
		} 
		if (contor == JocPuzzle.dim_puzzle * JocPuzzle.dim_puzzle) {         // daca toate celulele sunt conform solutiei
			System.out.print("Joc finalizat ");                              // afisam mesaj   
			try {                                              			// si sterg fisier de salvare joc (daca exista pe disc)
		         File fisier = new File("puzzle.xml");
		         if (fisier.exists()) {                                         // daca fisierul exista pe disc
					fisier.delete();                                            // il stergem
					}
		      } catch(Exception e) {
		         e.printStackTrace();
		      }	
			return true;
		}
		else {                                    // daca contor != dim * dim, inseamna ca nu toti indecsii tabelei de lucru
			return false;                         // sunt egali cu cei initiali (solutia) si iese cu fals
		}
	}
	
	
	
	public void AfisareTabla() {                                // va fi inlocuita in versiunile urmatoare de afisare in GUI
		for (int i = 0; i < JocPuzzle.dim_puzzle; i++) {
			for (int j = 0; j < JocPuzzle.dim_puzzle; j++) {
				System.out.print(tabla.elementAt(i*JocPuzzle.dim_puzzle+j).getVal() + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
}
