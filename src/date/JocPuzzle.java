package date;

import java.util.Scanner;                                                   // pentru citire de la tastatura
import java.util.Vector;
import java.io.File;                                                        // pentru verificare daca exista fisier XML pe disc

public class JocPuzzle {
	
	static int dim_puzzle = 0;                                               // dimensiunea puzzle-ului.....variabila globala

	public static void main(String[] args) {
		
		Scanner keyboard = new Scanner(System.in);                           // initializam citirea de la tastatura
		do {
			System.out.print("Marime puzzle (nr.impar): ");
			dim_puzzle = keyboard.nextInt();                                 // citim dimensiunea puzzle-ului
		} while(dim_puzzle % 2 == 0);                                        // pana se introduce un numar impar
		
		Vector<CelulaPuzzle> cp = new Vector<CelulaPuzzle>(dim_puzzle * dim_puzzle);          //vector cu celulele puzzle-ului
		for (int i = 0; i < dim_puzzle * dim_puzzle; i++) {        // pe care il vom copia intr-un obiect din clasa TablePuzzle
				CelulaPuzzle celulaCurenta = new CelulaPuzzle();
				celulaCurenta.setIndice(i);
				celulaCurenta.setVal(i+1);
				cp.add(celulaCurenta);
		}
		cp.lastElement().setVal(0);                                     // setam ultimul element ca fiind zero...celula goala
		
		int joc_nou = 0;                                               // pentru citire joc nou sau salvare de pe disc
		do {
			System.out.print("Joc nou (0) sau salvat de pe disc (1): ");
			joc_nou = keyboard.nextInt();
		} while(joc_nou < 0 || joc_nou > 1);
		TablaPuzzle tablaPuzzle = new TablaPuzzle();                         // puzzle de lucru,  pe care il vom afisa pe ecran
		tablaPuzzle.setTabla(cp);											// copiem vectorul de celule in tabla de lucru
		if (joc_nou == 0) {
			tablaPuzzle.AmestecareCelule();                                  // amestecam aleatoriu celulele tablei de lucru 
		} else {                                         // optiunea pentru citire date din fisier xml ... joc salvat anterior
			try {
		         File fisier = new File("puzzle.xml");
		         if (fisier.exists()) {                                         // daca fisierul exista pe disc
						tablaPuzzle = SerializareXML.deserializeazaXMP();         // citesc tablaPuzzle din fisier XML de pe disc
						dim_puzzle = (int)Math.sqrt(tablaPuzzle.getTabla().size()); // resetez var globala la dim tablei salvate pe disc
					} else {                                                      // daca nu exista fisier pe disc continuam cu joc nou
						tablaPuzzle.AmestecareCelule();
				         System.out.println("Nu exista fisier pe disc, continuam cu joc nou.");
					}
		      } catch(Exception e) {
		         e.printStackTrace();
		      }	
		}
		tablaPuzzle.AfisareTabla();

		int val = 0;                      // indicele celulei care se doreste a fi mutata sau -1 pentru salvare joc
		do { 
			System.out.print("Indice celula (012 345 678) care se doreste a fi mutata sau -1 pentru salvare joc: ");
			val = keyboard.nextInt();
			if (val == -1) {
				SerializareXML.serializeazaXML(tablaPuzzle); // salvez tablaPuzzle in fisier XML pe disc
				System.out.print("Jocul a fost salvat in fisier pe disc.");
			} else {
				tablaPuzzle.MutaCelula(val);
				tablaPuzzle.AfisareTabla();
			}
		} while((!tablaPuzzle.JocFinalizat()) && val != -1);

		keyboard.close();

	}
	
}

