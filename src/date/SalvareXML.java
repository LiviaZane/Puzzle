package date;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class SalvareXML {
														
	public static void scrieXML(TablaPuzzle c) {	                       
		try {
			XMLEncoder x = new XMLEncoder(new FileOutputStream("puzzle.xml")); 
			x.writeObject(c);
			x.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}	
	
	public static TablaPuzzle citesteXML() {                      
		TablaPuzzle cc = new TablaPuzzle();     
		try {
			XMLDecoder xx = new XMLDecoder(new FileInputStream("puzzle.xml"));
			cc = (TablaPuzzle)xx.readObject();							
			xx.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return cc;
	}
}