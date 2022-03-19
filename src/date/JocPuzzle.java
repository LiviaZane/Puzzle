package date;

import java.util.Scanner;                                                   
import java.util.Vector;

import javax.imageio.ImageIO;

import GUI.GUI_Aplicatie;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;                                                        

public class JocPuzzle {                                             

	public static void main(String[] args) {
		
		Scanner keyboard = new Scanner(System.in);                           // initializam citirea de la tastatura
		int dim = 0;
		do {
			System.out.print("Marime puzzle (nr.impar): ");
			dim = keyboard.nextInt();                                 
		} while(dim % 2 == 0);                                        // citim pana se introduce un numar impar
		
		Vector<CelulaPuzzle> cp = new Vector<CelulaPuzzle>(dim * dim);          
		for (int i = 0; i < dim * dim; i++) {        
				CelulaPuzzle celulaCurenta = new CelulaPuzzle();
				celulaCurenta.setIndice(i);
				celulaCurenta.setVal(i+1);
				cp.add(celulaCurenta);
		}
		cp.lastElement().setVal(0);                                     // setam ultimul element ca fiind zero...celula goala
		
		int joc_nou = 0;                                               
		do {
			System.out.print("Joc nou (0) sau salvat de pe disc (1): ");
			joc_nou = keyboard.nextInt();
		} while(joc_nou < 0 || joc_nou > 1);
		TablaPuzzle tablaPuzzle = new TablaPuzzle();                         
		if (joc_nou == 0) {
			tablaPuzzle.setTabla(cp);
			tablaPuzzle.setDim_puzzle(dim);
			tablaPuzzle.AmestecareCelule();                                  
		} else {                                         
			try {
		         File fisier = new File("puzzle.xml");
		         if (fisier.exists()) {                                         
						tablaPuzzle = SalvareXML.citesteXML();         								//
						dim = (int)Math.sqrt(tablaPuzzle.getTabla().size()); 
						tablaPuzzle.setDim_puzzle(dim);
					} else {   
						tablaPuzzle.setTabla(cp);
						tablaPuzzle.setDim_puzzle(dim);
						tablaPuzzle.AmestecareCelule();
				         System.out.println("Nu exista fisier pe disc, continuam cu joc nou.");
					}
		      } catch(Exception e) {
		         e.printStackTrace();
		      }	
		}
		tablaPuzzle.AfisareTabla();

		
		GUI_Aplicatie aplicatie = new GUI_Aplicatie(tablaPuzzle);
		tablaPuzzle.adaugareObservator(aplicatie);
		
		
		int val = 0;                      // indicele celulei care se doreste a fi mutata
		do { 
			System.out.print("Indice celula (012 345 678) care se doreste a fi mutata sau -1 pentru salvare joc: ");
			val = keyboard.nextInt();
			if (val == -1) {
				SalvareXML.scrieXML(tablaPuzzle); 
				System.out.print("Jocul a fost salvat in fisier pe disc.");
			} else {
				tablaPuzzle.MutaCelula(val);
				tablaPuzzle.AfisareTabla();
			}
		} while((!tablaPuzzle.JocFinalizat()) && val != -1);

		keyboard.close();
		
		
		
		// testare spargere imagine
		String a = "img/poza.jpg"; 	
    	BufferedImage imagini[] = new BufferedImage[dim * dim];    // creez vect img
    	try {
			imagini = Imagine.spargeImagine(a, dim);   // intoarce vectorul de imag -- trateaza exceptia primita cu throw din imagine.java
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	 //scriem de control subImaginile
        for (int i = 0; i < dim * dim; i++) {
            try {
				ImageIO.write(imagini[i], "png", new File("img/",i + ".png"));   // scrie fiecare fisier din vector
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        // testare spargere imagine
        

	}
	
}
