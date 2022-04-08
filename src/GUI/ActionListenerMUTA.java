package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import date.TablaPuzzle;

public class ActionListenerMUTA extends GUI_Aplicatie implements ActionListener {
	
	TablaPuzzle tablaPuzzle;
	
	public ActionListenerMUTA(TablaPuzzle tablaPuzz) {
		tablaPuzzle = tablaPuzz;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int cel = Integer.parseInt(((JButton) e.getSource()).getName());  // ia tot textul si il face int
		tablaPuzzle.MutaCelula(cel);
		if (tablaPuzzle.JocFinalizat()) {
			update(tablaPuzzle);
			JOptionPane.showMessageDialog(frame, "Felicitari! Ai finalizat jocul.");
			btn2.setEnabled(false);
			btn3.setEnabled(false);
			btn4.setEnabled(false);
		}
	}

}
