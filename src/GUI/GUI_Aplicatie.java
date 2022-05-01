package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
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
import javax.swing.JTextArea;

import date.CelulaPuzzle;
import date.TablaPuzzle;
import IA.Solutie;

public class GUI_Aplicatie implements Observator {
	protected static JFrame frame;         // mosteniri in subclase/extends....ActList RB, START, etc
	protected static JPanel panel;        // static pentru ca in sublcase se modifica 
	protected int dim_puzz;
	protected static JRadioButton rb1;
	protected static JRadioButton rb2;
	protected static JComboBox<String> aleg_poza;
	protected static JComboBox<Integer> cmb;
	protected static JButton btn1;
	protected static JButton btn2;
	protected static JButton btn3;
	protected static JButton btn4;
	protected static JTextArea text_solutie;
	protected static JTextArea text_solutie2;
	
	private static GUI_Aplicatie instance;                                            // Singleton
	public static GUI_Aplicatie getInstance(TablaPuzzle tablaPuzzle){
		if(instance == null) {
			instance = new GUI_Aplicatie(tablaPuzzle);
		} 
	    return instance;
	}
	
	private GUI_Aplicatie(TablaPuzzle tablaPuzzle) {         // private pt. ca instantiem cu Singleton
	    frame = new JFrame();
	    frame.setSize(1100, 740);
	    frame.setLayout(null);
	    initGUI(tablaPuzzle);                          // functie pentru instantiere butoane
	}
	
	protected GUI_Aplicatie() {} // constr implicit pentru subclase/extends....ActList RB, START, MUTA etc 
	
	private void initGUI(TablaPuzzle tablaPuzzle){
	    dim_puzz = tablaPuzzle.getDim_puzzle(); 
	    panel = new JPanel();                         // un container in care vom pune butoanele puzzle
	    panel.setLayout(null);
	    panel.setBounds(0, 0, 700, 700);
	    frame.add(panel);
	    
	    JLabel  label_tip_joc = new JLabel("Tip Puzzle : ");  
	    label_tip_joc.setBounds(700, 20, 70, 30);  
	    frame.add(label_tip_joc); 
	    
	    rb1 = new JRadioButton("Numeric", true);    
		rb2 = new JRadioButton("Poza");    
		rb1.setBounds(770, 20, 100, 30);    
		rb2.setBounds(770, 40, 100, 30);    
		ButtonGroup bg=new ButtonGroup();    
		bg.add(rb1);bg.add(rb2);  
		ActionListener listenerRB = new ActionListenerRB();
		rb1.addActionListener(listenerRB);
		rb2.addActionListener(listenerRB);
		frame.add(rb1);frame.add(rb2);   
		
		JLabel  label_dim = new JLabel("Dimensiune Puzzle : ");  
	    label_dim.setBounds(900, 20, 120, 30);  
	    frame.add(label_dim); 
		
		Integer cmb_list[] = {3, 5, 7, 9, 11, 13, 15, 17, 19, 21, 23, 25, 27, 29, 31, 35, 37, 39, 41};
		cmb = new JComboBox<Integer>(cmb_list);
		cmb.setMaximumRowCount(5);                         // selectam dimensiunea puzzle-ului
		cmb.setBounds(1020, 25, 50, 20);
		cmb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btn1.setEnabled(true);
			}
		});
		frame.add(cmb);
		
		JLabel  label_poza = new JLabel("Alege poza : ");  
	    label_poza.setBounds(780, 80, 80, 30);  
	    frame.add(label_poza); 
		
	    File director = new File("img/");
	    FilenameFilter filtru = new FilenameFilter() {    // filtru, numai pentru fisiere jpg
	    	@Override
	    	public boolean accept(File f, String s) {
	    		return s.endsWith(".jpg");
	    	}
	    };
	    String[] fisiere = director.list(filtru);
	    aleg_poza = new JComboBox<String>(fisiere);      // afisare numai fisiere jpg din folder /img
		aleg_poza.setBounds(860, 85, 200, 20);
		aleg_poza.setEnabled(false);
		frame.add(aleg_poza);
		
		btn1 = new JButton("Start joc");
		btn1.setBounds(720, 160, 200, 40);
		ActionListener listenerSTART = new ActionListenerSTART(tablaPuzzle);
		btn1.addActionListener(listenerSTART);
		btn1.setEnabled(false);
		frame.add(btn1);
		 
		btn2 = new JButton("Salveaza joc");
		btn2.setBounds(710, 240, 150, 30);
		btn2.setEnabled(false);
		ActionListener listenerSALV = new ActionListenerSALV(tablaPuzzle);
		btn2.addActionListener(listenerSALV);
		frame.add(btn2);
		 
		btn3 = new JButton("Incarca joc");
		btn3.setBounds(870, 240, 150, 30);
		ActionListener listenerINCARCA = new ActionListenerINCARCA(tablaPuzzle);
		btn3.addActionListener(listenerINCARCA);
		frame.add(btn3);
		
		btn4 = new JButton("Amesteca");
		btn4.setBounds(950, 160, 100, 40);
		ActionListener listenerAMESTEC = new ActionListenerAMESTEC(tablaPuzzle);
		btn4.addActionListener(listenerAMESTEC);
		frame.add(btn4);
		
	    // creare dim*dim JButoane pentru Puzzle
	    int i = 0, j = 0; // indicii de lin si col pentru matricea de JButtoane in care se afiseaza Puzzle
		Iterator<CelulaPuzzle> it = tablaPuzzle.getTabla().iterator();
		while(it.hasNext()) {                                                // utilizare sablon Iterator
			CelulaPuzzle tmp = it.next();
			String a = tmp.getVal();
			JButton b = new JButton();
			if(rb1.isSelected()) {
				b.setText(a);
				b.setFont(new Font("Arial", Font.PLAIN, 160/dim_puzz));
				if(tmp.getIndice() == dim_puzz*dim_puzz-1) {
		    		b.setForeground(Color.RED);
		    	}
			} else {
				ImageIcon icon = new ImageIcon("img/" + a + ".png");
				Image im = icon.getImage();
	    		Image im2 = im.getScaledInstance(650/dim_puzz, 650/dim_puzz, Image.SCALE_SMOOTH);
	    		b.setIcon(new ImageIcon(im2));		    		
			}
			b.setBounds(j*650/dim_puzz, i*650/dim_puzz, 650/dim_puzz, 650/dim_puzz); //x, y, l, h
	    	b.setMargin(new Insets(0, 0, 0, 0));
	    	b.setName(Integer.toString(i*dim_puzz+j));               // numele .... btn
	    	ActionListener listenerMUTA = new ActionListenerMUTA(tablaPuzzle);
			b.addActionListener(listenerMUTA);
	    	panel.add(b);
			if (++j % dim_puzz == 0) {
				i++;
				j = 0;
			}
		}
		
		JButton btn5 = new JButton("Solutie A-star Hamming");
		btn5.setBounds(710, 460, 165, 30);
		btn5.setMargin(new Insets(0, 0, 0, 0));
		btn5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[][] tabla_int = new int[dim_puzz][dim_puzz];
				for (int ii = 0; ii < dim_puzz; ii++) {
					for (int jj = 0; jj < dim_puzz; jj++) {
						tabla_int[ii][jj] = Integer.parseInt(tablaPuzzle.getTabla().elementAt(ii*dim_puzz+jj).getVal());
					}
				}
				text_solutie.setText(Solutie.solutiePuzzle(tabla_int, "Hamming"));
			}
		});
		frame.add(btn5);
		
		JButton btn6 = new JButton("Solutie A-star Manhattan");
		btn6.setBounds(880, 460, 165, 30);
		btn6.setMargin(new Insets(0, 0, 0, 0));
		btn6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[][] tabla_int = new int[dim_puzz][dim_puzz];
				for (int ii = 0; ii < dim_puzz; ii++) {
					for (int jj = 0; jj < dim_puzz; jj++) {
						tabla_int[ii][jj] = Integer.parseInt(tablaPuzzle.getTabla().elementAt(ii*dim_puzz+jj).getVal());
					}
				}
				text_solutie2.setText(Solutie.solutiePuzzle(tabla_int, "Manhattan"));
			}
		});
		frame.add(btn6);
				
		text_solutie = new JTextArea();             // afisare solutie Hamming
		text_solutie.setBounds(710,500,165,75);
		text_solutie.setLineWrap(true);
        frame.add(text_solutie);  
	    frame.setVisible(true);
	    
	    text_solutie2 = new JTextArea();               // afisare solutie Manhattan
		text_solutie2.setBounds(880,500,165,75);
		text_solutie2.setLineWrap(true);
        frame.add(text_solutie2);  
        
	    frame.setVisible(true);
	}                               // end initGUI
	
	
	@Override
	public void update(TablaPuzzle tablaPuzzle) {               // apelata in cadrul sablonului Observer
		int i = 0, j = 0;                                               // utilizare sablon Iterator
		Iterator<CelulaPuzzle> it = tablaPuzzle.getTabla().iterator();
		dim_puzz = tablaPuzzle.getDim_puzzle();
			while(it.hasNext()) {
				JButton b = (JButton) panel.getComponent(i*dim_puzz+j);
				CelulaPuzzle tmp = it.next();
				String a = tmp.getVal();
				if(rb1.isSelected()) {
					b.setText(a);                                            // text afisat in btn
					b.setFont(new Font("Arial", Font.PLAIN, 160/dim_puzz));
					if(tmp.getIndice() == dim_puzz*dim_puzz-1) {
			    		b.setForeground(Color.RED);
			    	} else {
			    		b.setForeground(Color.BLACK);
			    	}
				} else {
					ImageIcon icon = new ImageIcon("img/" + a + ".png");
					Image im = icon.getImage();
		    		Image im2 = im.getScaledInstance(650/dim_puzz, 650/dim_puzz, Image.SCALE_SMOOTH);
		    		b.setIcon(new ImageIcon(im2));		    		
				}
				b.setBounds(j*650/dim_puzz, i*650/dim_puzz, 650/dim_puzz, 650/dim_puzz); //x, y, l, h
				if (++j % dim_puzz == 0) {
					i++;
					j = 0;
				}
			}
			frame.repaint();
	}

}