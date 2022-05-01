package date;

import java.util.Iterator;
import java.util.Vector;
import GUI.Observator;
import java.io.File;

public class TablaPuzzle {
	
	private Vector<CelulaPuzzle> tabla;
	private int dim_puzzle;
	private Vector<Observator> observatori = new Vector<Observator>();

	
	public TablaPuzzle() {}                                           // constructor implicit
	
	public Vector<CelulaPuzzle> getTabla() {                          // getteri si setteri
		return tabla;
	}

	public void setTabla(Vector<CelulaPuzzle> tabla) {
		this.tabla = tabla;
	}
	
	
	public int getDim_puzzle() {
		return dim_puzzle;
	}

	public void setDim_puzzle(int dim_puzzle) {
		this.dim_puzzle = dim_puzzle;
	}


	public void adaugaObservator(Observator o) {
		observatori.add(o);
	}
	
	public void scoateObservator(Observator o) {
		observatori.remove(o);
	}
	
	
	
	//datele din celule raman neschimbate, numai locul (indicele) celulelor se schimba in vectorul tabla 
	public void MutaCelula(int indice_c) {   //c este celula selectata, care va face swap cu celula goala
		int indice_cg = CelulaGoala(indice_c);   // indicele celulei goale returnat de metoda CelulaGoala       
		if(indice_cg >= 0) {// CelulaGoala returneaza un indice intre [0, dim*dim) sau -1 daca nu are vecin celula goala
			CelulaPuzzle tmp = tabla.elementAt(indice_cg);                 //memoram celula goala in tmp
			tabla.setElementAt(tabla.elementAt(indice_c), indice_cg);      // in cg ducem c
			tabla.setElementAt(tmp, indice_c);                   // in c ducem tmp, adica am facut swap
			Iterator<Observator> it = observatori.iterator();
			while(it.hasNext()) {                                     // utilizare sablon Iterator
				it.next().update(this);
			}
		} else {
			//System.out.println("Nu poate fi mutata");
			//System.out.println();
		}
	}
	
	
	private int CelulaGoala(int indice_c) {    // c este celula selectata...returneaza indicele celulei vecine care este goala
		int i = indice_c / dim_puzzle;                 // sau -1 daca nici un vecin nu este celula goala
		int j = indice_c % dim_puzzle;
		if(i-1 >= 0 && tabla.elementAt((i-1)*(dim_puzzle)+j).getIndice() == dim_puzzle*dim_puzzle-1) {  // celula de deasupra lui c este goala
			return (i-1)*(dim_puzzle)+j;
		} else if(i+1 < dim_puzzle && tabla.elementAt((i+1)*(dim_puzzle)+j).getIndice() == dim_puzzle*dim_puzzle-1) {      // de sub 
			return (i+1)*(dim_puzzle)+j;
		} else if(j-1 >= 0 && tabla.elementAt(i*dim_puzzle+j-1).getIndice() == dim_puzzle*dim_puzzle-1) { //celula din stanga lui c este goala
			return i*dim_puzzle+j-1;
		} else if(j+1 < dim_puzzle && tabla.elementAt(i*dim_puzzle+j+1).getIndice() == dim_puzzle*dim_puzzle-1) {     // din dreapta
			return i*dim_puzzle+j+1;
		} else {                                                   // nici o celula vecina nu este goala
			return -1;
		}
	}

	
	
	public void AmestecareCelule() {   // se porneste de la solutie si se muta celule in locul celulei goale de dim*dim*dim ori
		int indice_vecin;                        // indice celula vecina aleatorie celulei goale
		int indice_cg = dim_puzzle * dim_puzzle - 1; //celula goala este ultima celula (initial)
		for (int i = 0; i < dim_puzzle*dim_puzzle*dim_puzzle; i++) {
			indice_vecin = VecinAleatorCelulaGoala(indice_cg);//swap intre celulaGoala si un vecin aleatoriu
			CelulaPuzzle tmp = tabla.elementAt(indice_cg);                //memoram celula goala in tmp
			tabla.setElementAt(tabla.elementAt(indice_vecin), indice_cg);// in locul celulei goale ducem celula vecina
			tabla.setElementAt(tmp, indice_vecin);                   // in vecin ducem celula goala (tmp)
			indice_cg = indice_vecin; // actualizam indice_cg ... devine indicele vecinului cu care a facut swap
		}
	}
	

	private int VecinAleatorCelulaGoala(int cg) {       // returneaza un vecin aleatoriu al celulei goale
		Vector<Integer> vecini = new Vector<>();               // vectorul in care stocam vecinii
		int i = cg / dim_puzzle;             // translatere indice celula goala in coordonate i si j
		int j = cg % dim_puzzle;           // pentru a putea "vedea" care celula e sus/jos/stanga/dreapta
		
		if(i-1 >= 0) {                                    // daca exista o celula deasupra celulei goale
			vecini.add((i-1)*(dim_puzzle)+j);                   // adaugam indicele ei la vectorul vecini
		} 
		if(i+1 < dim_puzzle) {                            // daca exista o celula sub celula goala
			vecini.add((i+1)*(dim_puzzle)+j);                   // adaugam indicele ei la vectorul vecini
		} 
		if(j-1 >= 0) {                                    // daca exista o celula in stanga celulei goale
			vecini.add(i*dim_puzzle+j-1);                       // adaugam indicele ei la vectorul vecini
		} 
		if(j+1 < dim_puzzle) {                            // daca exista o celula in dreapta celulei goale
			vecini.add(i*dim_puzzle+j+1);                       // adaugam indicele ei la vectorul vecini
		}
		
		return vecini.elementAt((int)(Math.random() * 10) % vecini.size());          //...random()->[0,1)
	}

	
	
	public Boolean JocFinalizat() {  // compara indecsii tablei de lucru cu indecsii initiali (solutia)
		int contor = 0;     // numara cate celule se afla in pozitia initiala (solutia)
		int index = 0;   // index care se icrementeaza la fiecare next() pentru comparare cu indice celula
		Iterator<CelulaPuzzle> it = this.tabla.iterator();    // utilizare sablon Iterator
			while(it.hasNext()) {
				if (it.next().getIndice() == index++) {
					contor++;
				}
			}
			
		if (contor == dim_puzzle * dim_puzzle) {         // daca toate celulele sunt conform solutiei
			this.tabla.lastElement().setVal(Integer.toString(dim_puzzle * dim_puzzle));  
			try {                               // si sterg fisier de salvare joc (daca exista pe disc)
		         File fisier = new File("puzzle.xml");
		         if (fisier.exists()) {                                // daca fisierul exista pe disc
					fisier.delete();                                            // il stergem
					}
		      } catch(Exception e) {
		         e.printStackTrace();
		      }	
			return true;
		}
		else {                // daca contor != dim * dim, inseamna ca nu toti indecsii tabelei de lucru
			return false;              // sunt egali cu cei initiali (solutia) si iese cu fals
		}
	}
	
}
