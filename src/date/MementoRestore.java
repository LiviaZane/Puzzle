package date;

import java.beans.XMLDecoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MementoRestore {

	public static MementoTablaPuzzle RestoreXML() {     // incarcare cc din fisier xml si transmiterea
		MementoTablaPuzzle cc = new MementoTablaPuzzle();           // acestuia prin return pentru incarcare tablaPuzzle
		try {
			XMLDecoder xx = new XMLDecoder(new FileInputStream("puzzle.xml"));
			cc = (MementoTablaPuzzle)xx.readObject();
			xx.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return cc;
	}
	
}
