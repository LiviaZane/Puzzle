package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import date.CelulaPuzzle;
import date.Imagine;
import date.SalvareXML;
import date.TablaPuzzle;

public class ActionListenerINCARCA extends GUI_Aplicatie implements ActionListener {
	
	TablaPuzzle tablaPuzzle;
	
	public ActionListenerINCARCA(TablaPuzzle tablaPuzz) {
		tablaPuzzle = tablaPuzz;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
	         File fisier = new File("puzzle.xml");
	         if (fisier.exists()) {                                         // daca fisierul exista pe disc
	        	 TablaPuzzle tablaPuzz = SalvareXML.citesteXML();           // citesc din XML
	        	 tablaPuzzle.getTabla().clear();
	        	 tablaPuzzle.setTabla(tablaPuzz.getTabla());
				 dim_puzz = tablaPuzz.getDim_puzzle();
				 tablaPuzzle.setDim_puzzle(dim_puzz);
					// spargere imagine
					if(rb2.isSelected()) {
						Vector<BufferedImage> imagini = new Vector<>(dim_puzz * dim_puzz);
						String aa = "img/" + aleg_poza.getSelectedItem(); 	
						try {
							imagini = Imagine.spargeImagine(aa, dim_puzz);
						} catch (Exception ee) {
							ee.printStackTrace();
						}
						File director1 = new File("img/");
						FilenameFilter filtru1 = new FilenameFilter() { // filtru pentru fisiere cu extensia png
							@Override
							public boolean accept(File f, String s) {
								return s.endsWith(".png");
							}
						};
						File fisiere1[] = director1.listFiles(filtru1);                // stergem fisierele .png
						for(File fis : fisiere1) {
							fis.delete();
						}
						//scriem subImaginile
						int ii = 1;                 //index nume fisier
						Iterator<BufferedImage> it = imagini.iterator();             // utilizare SABLON ITERATOR
						while(it.hasNext()) {
							try {
								ImageIO.write(it.next(), "png", new File("img/",ii++ + ".png"));
							} catch (IOException eee) {
								eee.printStackTrace();
							}
						}
					}// terminat cu imaginile
					panel.removeAll();  // stergem JButoanele din panel
					int i = 0, j = 0;
					Iterator<CelulaPuzzle> it = tablaPuzzle.getTabla().iterator();
					while(it.hasNext()) {                                             // utilizare SABLON ITERATOR
						CelulaPuzzle tmp = it.next();
						String a = tmp.getVal();
						JButton b = new JButton();
						if(rb1.isSelected()) {
							b.setText(a);                                // text afisat in btn
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
					update(tablaPuzzle);
					JOptionPane.showMessageDialog(frame, "Joc salvat din fisier de pe disc.");
					btn1.setEnabled(false);
					btn2.setEnabled(true);
					btn4.setEnabled(false);
				} else {                                           // daca nu exista fisier pe disc
					JOptionPane.showMessageDialog(frame, "Nu exista joc salvat, continuam cu acesta.");
				}
	      } catch(Exception ee) {
	         ee.printStackTrace();
	      }	
	}
}
