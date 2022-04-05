package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FilenameFilter;
import java.util.Iterator;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import date.CelulaPuzzle;
import date.TablaPuzzle;

public class GUI_Aplicatie implements Observator {
	private JFrame frame;
	private JPanel panel;
	private int dim_puzz;
	private JRadioButton rb1;  
	
	private static GUI_Aplicatie instance;                    // Singleton, constructor cu parametru
	public static GUI_Aplicatie getInstance(TablaPuzzle tablaPuzzle){
		if(instance == null) {
			instance = new GUI_Aplicatie(tablaPuzzle);
		} 
	    return instance;
	}
	
	private GUI_Aplicatie(TablaPuzzle tablaPuzzle) {     // private pt. ca vom instantia cu Singleton
		
	    frame = new JFrame();
	    frame.setSize(1100, 700);
	    frame.setLayout(null);
	    frame.setResizable(false);
	    
	    JLabel  label_tip_joc = new JLabel("Tip Puzzle : ");  
	    label_tip_joc.setBounds(700, 20, 70, 30);  
	    frame.add(label_tip_joc); 
	    
	    rb1 = new JRadioButton("Numeric", true);    
		JRadioButton rb2=new JRadioButton("Poza");    
		rb1.setBounds(770, 20, 100, 30);    
		rb2.setBounds(770, 40, 100, 30);    
		ButtonGroup bg=new ButtonGroup();    
		bg.add(rb1);bg.add(rb2);    
		frame.add(rb1);frame.add(rb2);   
		
		JLabel  label_dim = new JLabel("Dimensiune Puzzle : ");  
	    label_dim.setBounds(900, 20, 120, 30);  
	    frame.add(label_dim); 
		
		Integer cmb_list[] = {3, 5, 7, 9, 11, 13, 15, 17, 19, 21, 23, 25, 27, 29, 31, 35, 37, 39, 41};
		JComboBox<Integer> cmb = new JComboBox<Integer>(cmb_list);
		cmb.setMaximumRowCount(5);
		cmb.setBounds(1020, 25, 50, 20);
		frame.add(cmb);
		
		JLabel  label_poza = new JLabel("Alege poza : ");  
	    label_poza.setBounds(780, 80, 80, 30);  
	    frame.add(label_poza); 
		
	    File director = new File("img/");
	    FilenameFilter filtru = new FilenameFilter() {
	    	@Override
	    	public boolean accept(File f, String s) {
	    		return s.endsWith(".jpg");
	    	}
	    };
	    String[] fisiere = director.list(filtru);
	    JComboBox<String> aleg_poza = new JComboBox<String>(fisiere);
		aleg_poza.setBounds(860, 85, 200, 20);
		frame.add(aleg_poza);
		
		 JButton btn1 = new JButton("Start joc");
		 btn1.setBounds(770, 160, 200, 40);
		 frame.add(btn1);
		 JButton btn2 = new JButton("Salveaza joc");
		 btn2.setBounds(710, 240, 150, 30);
		 frame.add(btn2);
		 JButton btn3 = new JButton("Incarca joc");
		 btn3.setBounds(870, 240, 150, 30);
		 frame.add(btn3);
		
		
		dim_puzz = tablaPuzzle.getDim_puzzle(); 

	    panel = new JPanel();                         // un container in care vom pune butoanele puzzle
	    panel.setLayout(null);
	    panel.setBounds(0, 0, 700, 700);
	    
	    int i = 0, j = 0;
		Iterator<CelulaPuzzle> it = tablaPuzzle.getTabla().iterator();
		while(it.hasNext()) {
			CelulaPuzzle tmp = it.next();
			String a = tmp.getVal();
			JButton b = new JButton();
			if(rb1.isSelected()) {
				b.setText(a);                                            // text afisat in btn
				b.setBounds(j*650/dim_puzz, i*650/dim_puzz, 650/dim_puzz, 650/dim_puzz); //x, y, l, h
				b.setFont(new Font("Arial", Font.PLAIN, 160/dim_puzz));
				if(tmp.getIndice() == dim_puzz*dim_puzz-1) {
		    		b.setForeground(Color.RED);
		    	}
			} else {
				ImageIcon icon = new ImageIcon("img/" + (Integer.parseInt(tmp.getVal())-1) + ".png");
				b.setBounds(j*icon.getIconHeight(), i*icon.getIconWidth(), icon.getIconHeight(), icon.getIconWidth());
	    		b.setIcon(icon);                                      // imagine afisata in btn
			}
	    	b.setMargin(new Insets(0, 0, 0, 0));
	    	b.setName(Integer.toString(i*dim_puzz+j));               // numele .... btn
	    	b.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					int cel =Integer.parseInt(b.getName());         // ia tot textul si il face int
					tablaPuzzle.MutaCelula(cel);
				}
			});
	    	panel.add(b);
			if (++j % dim_puzz == 0) {
				i++;
				j = 0;
			}
		}
	    frame.add(panel);
	    frame.setVisible(true);
	}                               // end constructor
	
	
	@Override
	public void update(TablaPuzzle tablaPuzzle) {
		int i = 0, j = 0;
		Iterator<CelulaPuzzle> it = tablaPuzzle.getTabla().iterator();
			while(it.hasNext()) {
				JButton b = (JButton) panel.getComponent(i*dim_puzz+j);
				CelulaPuzzle tmp = it.next();
				String a = tmp.getVal();
				if(rb1.isSelected()) {  // nu merge rb1.isSelected()
					b.setText(a);                                            // text afisat in btn
					if(tmp.getIndice() == dim_puzz*dim_puzz-1) {
			    		b.setForeground(Color.RED);
			    	} else {
		    			b.setForeground(Color.BLACK);
		    		}
				} else {
					ImageIcon icon = new ImageIcon("img/" + (Integer.parseInt(tmp.getVal())-1) + ".png");
					b.setBounds(j*icon.getIconHeight(), i*icon.getIconWidth(), icon.getIconHeight(), icon.getIconWidth());
		    		b.setIcon(icon);                                      // imagine afisata in btn
				}
				if (++j % dim_puzz == 0) {
					i++;
					j = 0;
				}
	    		
			}
		frame.repaint();
	}
	
}