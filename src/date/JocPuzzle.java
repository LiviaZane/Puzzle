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
		tablaPuzzle.setTabla(cp);
		if (joc_nou == 0) {
			tablaPuzzle.setTabla(cp);                                        // copiem vectorul de celule in tabla de lucru
			tablaPuzzle.AmestecareCelule();                                  // amestecam aleatoriu celulele tablei de lucru 
		} else {                                         // optiunea pentru citire date din fisier xml ... joc salvat anterior
			try {
		         File fisier = new File("puzzle.xml");
		         if (fisier.exists()) {                                         // daca fisierul exista pe disc
						tablaPuzzle = SerializareXML.deserializeazaXMP();         // citesc tablaPuzzle din fisier XML de pe disc
						dim_puzzle = (int)Math.sqrt(tablaPuzzle.getTabla().size()); // resetez var globala la dim tablei salvate pe disc
					} else {                                                      // daca nu exista fisier pe disc
						tablaPuzzle.setTabla(cp);                                      // continuam cu joc nou
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



//in GUI o sa am o zona in care afisez celulele sub forma unor butoane
      // marimea butoanelor va fi calculata in functie de dimensiunea puzzle-ului
//cand se da click cu mouse pe o celula/buton, se face swap cu celula goala, 
      // numai daca ii este vecina celula goala, altfel afiseaza mesaj "Nu poate fi mutata"
//in alta zona din GUI o sa am butoane pentru:
      // SalvareJoc ... activ dupa AmestecareCelule si cat timp jocul nu este finalizat
      // IncarcareJocSalvat...activ daca exista un fisier in care a fost salvat jocul
		         // dupa o finalizare a unui joc, fisierul se sterge (daca exista pe disc)
      // JocNou ..... se reconstruiesc tablele, inclusiv se amesteca celulele 
//daca se apasa unul dintre butoane se iese din do-while cu break;
//pentru puzzle cu imagine, voi aduce dimensiunea imaginii la dimensiunea in care se afiseaza puzzle-ul,
		 // apoi o voi descompune in dim*dim parti, pe care le voi afisa in butoane 
//si daca va fi timp, voi pune si un buton/label "MutareaUrmatoare" ...solutia calculata cu BackTracking,
         // pentru ca se va iesi din solutia optima la fiecare mutare facuta altfel de jucator

//jocul nu poate avea o configuratie la un moment dat care sa nu aiba solutie, pentru ca metoda AmestecareCelule() 
//muta o celula in locul celulei goale vecina (conform regulei jocului) de dim*dim*dim ori, pornind de la solutie
        // din documentare am aflat ca daca e necesar un numar impar de swap-uri, jocul nu are solutie
	       // de exemplu 1 2 3   4 5 6  8 7 0 ... nu are solutie....nu poate fi finalizat...
