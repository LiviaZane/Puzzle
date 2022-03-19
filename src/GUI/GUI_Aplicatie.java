package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import date.TablaPuzzle;

public class GUI_Aplicatie implements Observator {
	private JFrame frame;
	private JPanel panel;
	private int dim_puzz;

	public GUI_Aplicatie(TablaPuzzle tablaPuzzle) {  
		dim_puzz = tablaPuzzle.getDim_puzzle(); 
	    frame = new JFrame("Joc Puzzle");                               // fereastra/window
	    frame.setSize(1100, 700);
	    frame.setLayout(null);
	    panel = new JPanel();                         // un container in care vom pune butoanele puzzle
	    panel.setLayout(null);
	    panel.setBounds(0, 0, 700, 700);
	    for (int i = 0; i < dim_puzz; i++) {
	    	for (int j = 0; j < dim_puzz; j++) {
	    		int a = tablaPuzzle.getTabla().elementAt(i*dim_puzz+j).getVal();
	    		JButton b = new JButton(Integer.toString(a));                  // text afisat in btn
	    		b.setBounds(j*650/dim_puzz, i*650/dim_puzz, 650/dim_puzz, 650/dim_puzz); //x, y, l, h
	    		b.setMargin(new Insets(0, 0, 0, 0));
	    		b.setFont(new Font("Arial", Font.PLAIN, 160/dim_puzz));
	    		if(a == 0) {
	    			b.setForeground(Color.RED);
	    		}
	    		b.setName(String.valueOf(i*dim_puzz+j));               // numele .... btn
	    		b.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						int i=Integer.parseInt(b.getName());         // ia tot textul si il face int
						tablaPuzzle.MutaCelula(i);
					}
				});
	    		panel.add(b);
	    	}
	    }
	    frame.add(panel);
	    
	    JButton btn1 = new JButton("Joc nou");
	    btn1.setBounds(770, 20, 190, 30);
	    frame.add(btn1);
	    JButton btn2 = new JButton("Salveaza joc");
	    btn2.setBounds(770, 80, 190, 30);
	    frame.add(btn2);
	    JButton btn3 = new JButton("Incarca joc");
	    btn3.setBounds(770, 150, 190, 30);
	    frame.add(btn3);
	    frame.setVisible(true);
	}                               // end constructor
	
	
	
	@Override
	public void update(TablaPuzzle tablaPuzzle) {
		for (int i = 0; i < dim_puzz; i++) {
	    	for (int j = 0; j < dim_puzz; j++) {
	    		int a = tablaPuzzle.getTabla().elementAt(i*dim_puzz+j).getVal();
	    		JButton b = (JButton) panel.getComponent(i*dim_puzz+j);
	    		b.setText(Integer.toString(a));
	    		if(a == 0) {
	    			b.setForeground(Color.RED);
	    		} else {
	    			b.setForeground(Color.BLACK);
	    		}
	    	}
	    }
		frame.repaint();
	}
	
}