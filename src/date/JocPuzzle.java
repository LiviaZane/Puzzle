package date;

import java.util.Scanner;                                                   // pentru citire de la tastatura
import java.util.Vector;

import javax.imageio.ImageIO;

import GUI.GUI_Aplicatie;

import java.awt.image.BufferedImage;
import java.io.File;                                                        // pentru verificare daca exista fisier XML pe disc
import java.io.IOException;

public class JocPuzzle {
	
	public static void main(String[] args) {
		
		int dimensiune_puzzle = 0;
		
		Scanner keyboard = new Scanner(System.in);                           // initializam citirea de la tastatura
		do {
			System.out.println("Marime puzzle (nr.impar): ");
			dimensiune_puzzle = keyboard.nextInt();                                 
		} while(dimensiune_puzzle % 2 == 0);                                          
		
		Vector<CelulaPuzzle> cp = new Vector<CelulaPuzzle>(dimensiune_puzzle * dimensiune_puzzle);          //vector cu celulele puzzle-ului
		for (int i = 0; i < dimensiune_puzzle * dimensiune_puzzle; i++) {	
			// CelulaPuzzle celulaCurenta = new CelulaPuzzle();            // instantiere initiala	
			
			// FactoryCelulaPuzzle factory = new FactoryCelulaPuzzle();      // inlocuire cu AbstractFactory (CelulaPuzzle)
			// CelulaPuzzle celulaCurenta = factory.creareCelulaPuzzle();
			
			// FactoryCelulaPuzzle_sterge factory = new FactoryCelulaPuzzle_sterge();  // inlocuire cu AbstractFactory (CelulaPuzzle_sterge)
			// CelulaPuzzle_sterge celulaCurenta = (CelulaPuzzle_sterge) factory.creareCelulaPuzzle();
			
			// celulaCurenta.setIndice(i);                     // inlocuite de sablon Builder
			// celulaCurenta.setVal(Integer.toString(i+1));
			
			
			
			
			CelulaPuzzle celulaCurenta = new CelulaPuzzle.Builder().creazaIndice(i).creazaVal(Integer.toString(i+1)).build();
			cp.add(celulaCurenta);
			
		
		}
		cp.lastElement().setVal(Integer.toString(0));                                     // setam ultimul element ca fiind zero...celula goala
		
		int joc_nou = 0;                                               // pentru citire joc nou sau salvare de pe disc
		do {
			System.out.println("Joc nou (0) sau salvat de pe disc (1): ");
			joc_nou = keyboard.nextInt();
		} while(joc_nou < 0 || joc_nou > 1);
		TablaPuzzle tablaPuzzle = new TablaPuzzle();                         // puzzle de lucru,  pe care il vom afisa pe ecran
		if (joc_nou == 0) {
			tablaPuzzle.setTabla(cp);                                        // copiem vectorul de celule in tabla de lucru
			tablaPuzzle.setDim_puzzle(dimensiune_puzzle);
			tablaPuzzle.AmestecareCelule();                                  // amestecam aleatoriu celulele tablei de lucru 
		} else {                                         // optiunea pentru citire date din fisier xml ... joc salvat anterior
			try {
		         File fisier = new File("puzzle.xml");
		         if (fisier.exists()) {                                         // daca fisierul exista pe disc
						tablaPuzzle = SalvareXML.citesteXML();         // citesc tablaPuzzle din fisier XML de pe disc
						dimensiune_puzzle = (int)Math.sqrt(tablaPuzzle.getTabla().size()); // resetez var globala la dim tablei din xml
						tablaPuzzle.setDim_puzzle(dimensiune_puzzle);
					} else {                                                                     // daca nu exista fisier pe disc
						tablaPuzzle.setTabla(cp);                                                // continuam cu joc nou
						tablaPuzzle.setDim_puzzle(dimensiune_puzzle);
						tablaPuzzle.AmestecareCelule();
				        System.out.println("Nu exista fisier pe disc, continuam cu joc nou.");
					}
		      } catch(Exception e) {
		         e.printStackTrace();
		      }	
		}
		tablaPuzzle.AfisareTabla();
		
		// GUI_Aplicatie aplicatie = new GUI_Aplicatie(tablaPuzzle);           // instantiere initiala
		GUI_Aplicatie aplicatie = GUI_Aplicatie.getInstance(tablaPuzzle);  // utilizare Singleton
		
		tablaPuzzle.adaugaObservator(aplicatie);

		int val = 0;                      // indicele celulei care se doreste a fi mutata sau -1 pentru salvare joc
		do { 
			System.out.println("Indice celula (012 345 678) care se doreste a fi mutata sau -1 pentru salvare joc: ");
			val = keyboard.nextInt();
			if (val == -1) {
				SalvareXML.scrieXML(tablaPuzzle); // salvez tablaPuzzle in fisier XML pe disc
				System.out.println("Jocul a fost salvat in fisier pe disc.");
			} else {
				tablaPuzzle.MutaCelula(val);
				tablaPuzzle.AfisareTabla();
			}
		} while((!tablaPuzzle.JocFinalizat()) && val != -1);

		
		// testare spargere imagine
				String a = "img/poza.jpg"; 	
		    	BufferedImage imagini[] = new BufferedImage[dimensiune_puzzle * dimensiune_puzzle];    // creez vect img
		    	try {
					imagini = Imagine.spargeImagine(a, dimensiune_puzzle);   // intoarce vectorul de imag -- trateaza exceptia primita cu throw din imagine.java
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	
		    	 //scriem de control subImaginile
		        for (int i = 0; i < dimensiune_puzzle * dimensiune_puzzle; i++) {
		            try {
						ImageIO.write(imagini[i], "png", new File("img/",i + ".png"));   // scrie fiecare fisier din vector
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        }
		        System.out.println("Poza.jpg a fost sparta in " + Integer.toString(dimensiune_puzzle * dimensiune_puzzle)+ " fisiere .png");
		 // testare spargere imagine
		
		keyboard.close();

	}
	
}
