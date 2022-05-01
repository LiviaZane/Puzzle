package IA;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

public class Solutie {

	public class Nod{   // nod al arborelui de cautare  
		int g;           // al catelea copil este....pe ce nivel din arbore se gaseste nodul  
		int h;           // rezultatul functiilor euristice (Hamming si drumul Manhattan)
		int f;           // functia de cautare euristica....... f=g+h
		int tabla[][];   // tabla aferenta nodului
		int r;int c;     // indecsii rand/coloana ai celulei goale
		String cale = "";  // pentru memorare cale sus/jos/stanga/dreapta....cum au fost generati copii
		
		public Nod(int g,int h,int tabla[][],int r,int c, String cale){
			this.g=g;
			this.h=h;
			this.tabla=tabla;
			this.f=g+h;
			this.r=r;this.c=c;
			this.cale = cale;
		}
	}

	 public static String solutiePuzzle(int[][] tabla, String sol) {
	        int rand=0, coloana=0;                       // indecsi celula goala
	        Set<String> nod_vizitat = new HashSet<>();   // set (lista cu elemente unice) cu hashcode
	        
	        PriorityQueue<Nod> lista_noduri = new PriorityQueue<>((a,b)-> a.f-b.f); // lista cu prioritate,
	                                                               // ordonata automat dupa valoarea lui f
	        boolean gasit_cel_goala = false;
	        for(int i=0;i<tabla.length;i++){
	            for(int j=0;j<tabla[0].length;j++){
	                if(tabla[i][j]==0){
	                    rand = i; coloana = j;
	                    gasit_cel_goala = true;
	                    break;
	                }
	            }
	            if(gasit_cel_goala)
	            	break;
	        }
	        
	        int tabla_initiala[][]=copie(tabla);       // se face o copie a tablei,
	        nod_vizitat.add(hash(tabla_initiala));     // se adauga la lista_noduri si la vizitate
	        Solutie x = new Solutie();                 // instantiere pentru utlizare x.new
	        if (sol == "Hamming")
	        	lista_noduri.add(x.new Nod(0,Hamming(tabla_initiala),tabla_initiala,rand,coloana, ""));
	        else
	        	lista_noduri.add(x.new Nod(0,Manhattan(tabla_initiala),tabla_initiala,rand,coloana, ""));
	        while(lista_noduri.size()!=0) {          // se parcurge lista_noduri
	            Nod parinte=lista_noduri.poll();
	            int curent[][]=parinte.tabla;
	            String cale_cur = parinte.cale;
	            if(parinte.h==0) {                  // daca toate celulele sunt la locul lor
	            	String solutia = parinte.g + " mutari -> " + parinte.cale;
	            	return solutia;
	            }
	            
	            int r=parinte.r;
	            int c=parinte.c;
	            
	            int copil1[][]=copil_nou(curent,r,c,r+1,c);    // se genereaza cei 2 - 4 copii (noduri noi)
	            int copil2[][]=copil_nou(curent,r,c,r-1,c);
	            int copil3[][]=copil_nou(curent,r,c,r,c+1);
	            int copil4[][]=copil_nou(curent,r,c,r,c-1);
	            String s1=hash(copil1);                  // se genereaza string pentru fiecare nod nou
	            String s2=hash(copil2);                  // 2,3,4,1,5.......
	            String s3=hash(copil3);
	            String s4=hash(copil4);
	            
	            if(copil1!=null&&!nod_vizitat.contains(s1)){  // se verifica sa nu fie null nodul si
	                nod_vizitat.add(s1);//sa nu existe in lista de noduri vizitate apoi se adauga la cele 2 liste
	                String cale_jos = cale_cur + " jos"; // in functie de noul nod, se completeaza calea
	                if (sol == "Hamming")
	                	lista_noduri.add(x.new Nod(parinte.g+1,Hamming(copil1),copil1,r+1,c, cale_jos));
	                else
	                	lista_noduri.add(x.new Nod(parinte.g+1,Manhattan(copil1),copil1,r+1,c, cale_jos));
	            }
	            if(copil2!=null&&!nod_vizitat.contains(s2)){
	                nod_vizitat.add(s2);
	                String cale_sus = cale_cur + " sus";
	                if (sol == "Hamming")
	                	lista_noduri.add(x.new Nod(parinte.g+1,Hamming(copil2),copil2,r-1,c, cale_sus));
	                else
	                	lista_noduri.add(x.new Nod(parinte.g+1,Manhattan(copil2),copil2,r-1,c, cale_sus));
	            }
	            if(copil3!=null&&!nod_vizitat.contains(s3)){
	                nod_vizitat.add(s3);
	                String cale_dr = cale_cur + " dr";
	                if (sol == "Hamming")
	                	lista_noduri.add(x.new Nod(parinte.g+1,Hamming(copil3),copil3,r,c+1, cale_dr));
	                else
	                	lista_noduri.add(x.new Nod(parinte.g+1,Manhattan(copil3),copil3,r,c+1, cale_dr));
	            }
	            if(copil4!=null&&!nod_vizitat.contains(s4)){
	                nod_vizitat.add(s4);
	                String cale_st = cale_cur + " st";
	                if (sol == "Hamming")
	                	lista_noduri.add(x.new Nod(parinte.g+1,Hamming(copil4),copil4,r,c-1, cale_st));
	                else
	                	lista_noduri.add(x.new Nod(parinte.g+1,Manhattan(copil4),copil4,r,c-1, cale_st));
	            }
	        }
	        
	        return "Nu are solutie sau prea multe nivele de cautare (out of memory heap space)";
	    }
	    
	    // numarul de celule care nu sunt la locul lor (conform solutiei)
	    public static int Hamming(int tabla[][]){
	        int cur=1;
	        int h=0;
	        for(int i=0;i<tabla.length;i++){
	            for(int j=0;j<tabla[0].length;j++){
	                if(tabla[i][j]!=cur)
	                	h++;
	                cur++;
	                cur%=tabla.length*tabla[0].length;
	            }
	        }
	        return h;
	    }
	    
	     // suma distantelor Manhattan.....distanta de la locul in care se afla celula 
	    public static int Manhattan(int tabla[][]) { // pana la locul in care ar trebui sa fie conform solutiei
	    	int count = 0;
	    	int expected = 0;
	    	for (int row = 0; row < tabla.length; row++) {
	    		for (int col = 0; col < tabla[row].length; col++) {
	    			int value = tabla[row][col];
	    			expected++;
	    			if (value != 0 && value != expected) {
	    				count += Math.abs(row - (int) ((value-1)/tabla.length)) + Math.abs(col - (int) ((value-1)%tabla.length));                
	    			}
	    		}	
	    	}
	    	return count;
	    }

	    public static String hash(int tabla[][]){
	        if(tabla==null)return "";
	        StringBuilder str = new StringBuilder();
	         for(int i=0;i<tabla.length;i++){
	            for(int j=0;j<tabla[0].length;j++){
	                str.append(tabla[i][j]+",");
	            }
	        }
	        return str.toString();
	    }
	    
	    public static int[][] copie(int tabla[][]){
	    	int r=tabla.length;
	        int c=tabla[0].length;
	        int copie_tabla[][]=new int[r][c];
	        for(int i=0;i<r;i++){
	            for(int j=0;j<c;j++){
	                copie_tabla[i][j]=tabla[i][j];
	            }
	        }
	        return copie_tabla;
	    }
	    
	    public static int[][] copil_nou(int tabla[][],int r1,int c1,int r2,int c2){
	        if(r2<0||c2<0||r2>=tabla.length||c2>=tabla[0].length)
	        	return null;
	        int rez[][]=copie(tabla);
	        int temp=rez[r1][c1];
	        rez[r1][c1]=rez[r2][c2];
	        rez[r2][c2]=temp;
	        return rez;
	    }

}
