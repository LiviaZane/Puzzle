package date;

import java.util.Vector;

import GUI.Observator;

import java.io.File;

public class TablaPuzzle {
	
	private Vector<CelulaPuzzle> tabla;
	private int dim_puzzle;
	private Vector<Observator> observatori = new Vector<Observator>();
	
	public TablaPuzzle() {}                                                           
	
	public Vector<CelulaPuzzle> getTabla() {                                           
		return tabla;
	}

	public void setTabla(Vector<CelulaPuzzle> tabla) {
		this.tabla = tabla;
	}
	
	public int getDim_puzzle() {
		return dim_puzzle;
	}

	public void setDim_puzzle(int din_puzzle) {
		this.dim_puzzle = din_puzzle;
	}
	
	public void adaugareObservator(Observator o) {
		observatori.add(o);
	}
	
	public void scoateObservator(Observator o) {
		observatori.remove(o);
	}

	
	//datele din celule raman neschimbate, numai locul (indicele) celulelor se schimba in vectorul tabla 
	public void MutaCelula(int indice_c) {               
		int indice_cg = CelulaGoala(indice_c);                  
		if(indice_cg >= 0) {        
			CelulaPuzzle tmp = tabla.elementAt(indice_cg);                 
			tabla.setElementAt(tabla.elementAt(indice_c), indice_cg);      
			tabla.setElementAt(tmp, indice_c);    
			for (int i=0; i<observatori.size(); i++) {
				observatori.get(i).update(this);
			}
			
		} else {
			System.out.println("Nu poate fi mutata");
			System.out.println();
		}
	}
	
	
	private int CelulaGoala(int indice_c) {    
		int i = indice_c / dim_puzzle;                 
		int j = indice_c % dim_puzzle;
		if(i-1 >= 0 && tabla.elementAt((i-1)*(dim_puzzle)+j).getIndice() == dim_puzzle*dim_puzzle-1) {  
			return (i-1)*(dim_puzzle)+j;
		} else if(i+1 < dim_puzzle && tabla.elementAt((i+1)*(dim_puzzle)+j).getIndice() == dim_puzzle*dim_puzzle-1) {      
			return (i+1)*(dim_puzzle)+j;
		} else if(j-1 >= 0 && tabla.elementAt(i*dim_puzzle+j-1).getIndice() == dim_puzzle*dim_puzzle-1) { 
			return i*dim_puzzle+j-1;
		} else if(j+1 < dim_puzzle && tabla.elementAt(i*dim_puzzle+j+1).getIndice() == dim_puzzle*dim_puzzle-1) {    
			return i*dim_puzzle+j+1;
		} else {                                                                       
			return -1;
		}
	}

	
	
	public void AmestecareCelule() {  
		int indice_vecin;                       
		int indice_cg = dim_puzzle * dim_puzzle - 1; 
		for (int i = 0; i < dim_puzzle*dim_puzzle*dim_puzzle; i++) {
			indice_vecin = VecinAleatorCelulaGoala(indice_cg);     
			CelulaPuzzle tmp = tabla.elementAt(indice_cg);                     
			tabla.setElementAt(tabla.elementAt(indice_vecin), indice_cg);      
			tabla.setElementAt(tmp, indice_vecin);                            
			indice_cg = indice_vecin;     
		}
	}
	

	private int VecinAleatorCelulaGoala(int cg) {         
		Vector<Integer> vecini = new Vector<>();              
		int i = cg / dim_puzzle;             
		int j = cg % dim_puzzle;             
		
		if(i-1 >= 0) {                                              
			vecini.add((i-1)*(dim_puzzle)+j);                  
		} 
		if(i+1 < dim_puzzle) {                    
			vecini.add((i+1)*(dim_puzzle)+j);                   
		} 
		if(j-1 >= 0) {                                              
			vecini.add(i*dim_puzzle+j-1);                      
		} 
		if(j+1 < dim_puzzle) {                            
			vecini.add(i*dim_puzzle+j+1);                       // adaugam indicele ei la vectorul vecini
		}
		
		return vecini.elementAt((int)(Math.random() * 10) % vecini.size());  // returneaza un vecin aleatoriu...random()->[0,1)
	}

	
	
	public Boolean JocFinalizat() {                         // compara indecsii tablei de lucru cu indecsii initiali (solutia)
		int contor = 1;
		if (this.tabla.lastElement().getIndice() == dim_puzzle*dim_puzzle-1) {                
			for (int i = 0; i < dim_puzzle * dim_puzzle-1; i++) {  
				if (this.tabla.elementAt(i).getIndice() == i) {
					contor++;
				}
			}
		} 
		if (contor == dim_puzzle * dim_puzzle) {      
			System.out.print("Joc finalizat ");                           
			try {                                              			
				File fisier = new File("puzzle.xml");
		         if (fisier.exists()) {                                         // daca fisierul exista deja pe disc il stergem
					fisier.delete();                                           
					}
		      } catch(Exception e) {
		         e.printStackTrace();
		      }	
			return true;
		}
		else {                                    
			return false;                        
		}
	}
	
	public void AfisareTabla() {                                
		for (int i = 0; i < dim_puzzle; i++) {
			for (int j = 0; j < dim_puzzle; j++) {
				System.out.print(tabla.elementAt(i*dim_puzzle+j).getVal() + " ");
			}
			System.out.println();
		}
//		System.out.println();
	}
	
}

//
