package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JRadioButton;

public class ActionListenerRB extends GUI_Aplicatie implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		JRadioButton btn = (JRadioButton)e.getSource();
		if(btn.getText() == "Numeric") {
			aleg_poza.setEnabled(false);
		} else {
			aleg_poza.setEnabled(true);
		}	
	}
}
