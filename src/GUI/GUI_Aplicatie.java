package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FilenameFilter;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import date.TablaPuzzle;

public class GUI_Aplicatie implements Observator {
	private JFrame frame;
	private JPanel panel;
	private int dim_puzz;

	// singleton, instantiaza un sg obiect pt o clasa
	
	
	private static GUI_Aplicatie instance; // static pt ca este aceeasi pt toate obiectele din clasa
	public static GUI_Aplicatie getInstance(TablaPuzzle tablaPuzzle) {
		if(instance == null) {
			instance = new GUI_Aplicatie(tablaPuzzle);
		}
		return instance;
	}
	
	
	private GUI_Aplicatie(TablaPuzzle tablaPuzzle) {   		// privat pt ca vom instantia cu Singleton
		dim_puzz = tablaPuzzle.getDim_puzzle();          
	    frame = new JFrame("Joc Puzzle");                               
	    frame.setSize(1100, 700);
	    frame.setResizable(false);
	    frame.setLayout(null);
	    
	    JLabel label_tip_joc = new JLabel("Tip Puzzle: ");
	    label_tip_joc.setBounds(700,20,70,30);
	    frame.add(label_tip_joc);
	    
	    JRadioButton rb1 = new JRadioButton ("Numeric", true);
	    JRadioButton rb2 = new JRadioButton ("Poza");
	    rb1.setBounds(770, 20, 100, 30);
	    rb2.setBounds(770, 40, 100, 30);
	    ButtonGroup bg = new ButtonGroup();
	    bg.add(rb1);
	    bg.add(rb2);
	    frame.add(rb1);
	    frame.add(rb2);
	    
	    JLabel label_dim = new JLabel("Dimensiune Puzzle: ");
	    label_dim.setBounds(900,20,120,30);
	    frame.add(label_dim);
	    
	    Integer cmb_list[] = {3,5,7,9,11,13,15,17,19,21,23,25,27,29,31,33,35,37,39};
	    JComboBox<Integer> cmb = new JComboBox<Integer>(cmb_list);
	    cmb.setMaximumRowCount(5);
	    cmb.setBounds(1020,25,50,20);	    
	    frame.add(cmb);
	    
	    JLabel label_poza = new JLabel("Alege poza: ");
	    label_poza.setBounds(780,80,80,30);
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
	    frame.setVisible(true);
	    
	    panel = new JPanel();                         
	    panel.setLayout(null);
	    panel.setBounds(0, 0, 700, 700);             
	    
	    for (int i = 0; i < dim_puzz; i++) {
	    	for (int j = 0; j < dim_puzz; j++) {
	    		String a = tablaPuzzle.getTabla().elementAt(i*dim_puzz+j).getVal();   
	    		JButton b = new JButton(a);                 
	    		b.setBounds(j*650/dim_puzz, i*650/dim_puzz, 650/dim_puzz, 650/dim_puzz);        
	    		b.setMargin(new Insets(0, 0, 0, 0));    
	    		b.setFont(new Font("Arial", Font.PLAIN, 160/dim_puzz));  
	    		if(tablaPuzzle.getTabla().get(i*dim_puzz + j).getIndice() == dim_puzz*dim_puzz-1) {
	    			b.setForeground(Color.RED);
	    		}
	    		b.setName(Integer.toString(i*dim_puzz+j));               
	    		b.addActionListener(new ActionListener()  
				{
					public void actionPerformed(ActionEvent e) 
					{
						int i=Integer.parseInt(b.getName());         
						tablaPuzzle.MutaCelula(i);        
					}
				});
	    		panel.add(b);
	    	}
	    }
	    frame.add(panel);
	}                               
		
	
	@Override
	public void update(TablaPuzzle tablaPuzzle) {   
		for (int i = 0; i < dim_puzz; i++) {
	    	for (int j = 0; j < dim_puzz; j++) {
	    		String a = tablaPuzzle.getTabla().elementAt(i*dim_puzz+j).getVal();
	    		JButton b = (JButton) panel.getComponent(i*dim_puzz+j);
	    		b.setText(a);
	    		if(tablaPuzzle.getTabla().get(i*dim_puzz + j).getIndice() == dim_puzz*dim_puzz-1) {
	    			b.setForeground(Color.RED);
	    		} else {
	    			b.setForeground(Color.BLACK); 
	    		}
	    	}
	    }
		frame.repaint();
	}
	
}

//