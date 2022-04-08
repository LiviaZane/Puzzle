package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import date.SalvareXML;
import date.TablaPuzzle;

public class ActionListenerSALV extends GUI_Aplicatie implements ActionListener {
	
	TablaPuzzle tablaPuzzle;
	
	public ActionListenerSALV(TablaPuzzle tablaPuzz) {
		tablaPuzzle = tablaPuzz;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		SalvareXML.scrieXML(tablaPuzzle); // salvez tablaPuzzle in fisier XML pe disc
		// trebuie sa mai completez cu salvare imagini....
		JOptionPane.showMessageDialog(frame, "Jocul a fost salvat in fisier pe disc.");
		btn3.setEnabled(true);
	}

}
