package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import date.TablaPuzzle;

public class ActionListenerAMESTEC extends GUI_Aplicatie implements ActionListener {
	
	TablaPuzzle tablaPuzzle;
	
	public ActionListenerAMESTEC(TablaPuzzle tablaPuzz) {
		tablaPuzzle = tablaPuzz;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		tablaPuzzle.getTabla().lastElement().setVal(Integer.toString(0)); // ultimul element zero
		tablaPuzzle.AmestecareCelule();
		btn1.setEnabled(false);
		btn2.setEnabled(true);
		btn3.setEnabled(true);
		btn4.setEnabled(false);
		update(tablaPuzzle);
	}

}
