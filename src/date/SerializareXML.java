package date;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class SerializareXML {
														
	public static void serializeazaXML(TablaPuzzle c) {	                       // salvare c (tablaPuzzle) in fisier xml
		try {
			XMLEncoder x = new XMLEncoder(new FileOutputStream("puzzle.xml"));  //conversie
			x.writeObject(c);
			x.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}	
	
	public static TablaPuzzle deserializeazaXMP() {                       // incarcare cc din fisier xml si transmiterea
		TablaPuzzle cc = new TablaPuzzle();     // acestuia prin return pentru incarcare tablaPuzzle
		try {
			XMLDecoder xx = new XMLDecoder(new FileInputStream("puzzle.xml"));
			cc = (TablaPuzzle)xx.readObject();							//cast la Tabla Puzzle
			xx.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return cc;
	}

}