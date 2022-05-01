package date;

import java.beans.XMLEncoder;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class MementoCreate {
	
	public static void CreateXML(MementoTablaPuzzle c) {
		// MementoTablaPuzzle memento = new MementoTablaPuzzle(tablaPuzz.getTabla(), tablaPuzz.getDim_puzzle());
		
		try {
			XMLEncoder x = new XMLEncoder(new FileOutputStream("puzzle.xml"));
			x.writeObject(c);
			x.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
