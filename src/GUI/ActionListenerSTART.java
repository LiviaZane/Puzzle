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

import date.Bridge2_implementare_celulapuzzle;
import date.Bridge3_abstract_celulapuzzle;
import date.Bridge4_implementare_celulapuzzle_abstract;
import date.CelulaPuzzle;
import date.Facade;
import date.FactoryCelulaPuzzle;
import date.Imagine;
import date.MediatorUser;
import date.TablaPuzzle;
import date.TemplateCelula;
import date.TemplateConcretCelula_puzzle;

public class ActionListenerSTART extends GUI_Aplicatie implements ActionListener {
	
	TablaPuzzle tablaPuzzle;
	
	public ActionListenerSTART(TablaPuzzle tablaPuzz) {
		tablaPuzzle = tablaPuzz;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		dim_puzz = (int)cmb.getSelectedItem();
		tablaPuzzle.setDim_puzzle(dim_puzz);
		tablaPuzzle.getTabla().clear();

		for (int i = 0; i < dim_puzz * dim_puzz; i++) {
			if (i%2 == 0) {                                                    // pentru i par, utilizez
				FactoryCelulaPuzzle factory = new FactoryCelulaPuzzle();             // sablonul Factory
				CelulaPuzzle celulaCurenta = factory.creareCelulaPuzzle();
				celulaCurenta.setIndice(i);
				celulaCurenta.setVal(Integer.toString(i+1));
				tablaPuzzle.getTabla().add(celulaCurenta);
			} else if (i%3 == 0) {                                  // pentru i divizibil cu 3, utilizez
				TemplateCelula celulaTemplate = new TemplateConcretCelula_puzzle(); // sablonul Template
				celulaTemplate.setIndice(i);
				celulaTemplate.setVal(Integer.toString(i+1));
				tablaPuzzle.getTabla().add(celulaTemplate.getCelula());
			} else if (i%5 == 0) {                                  // pentru i divizibil cu 5, utilizez
				Facade facade = new Facade();                                         // sablonul Facade
				CelulaPuzzle celulaCurenta = facade.makeCelulaPuzzle(i);
				tablaPuzzle.getTabla().add(celulaCurenta);
			} else if (i%7 == 0) {                                   // pentru i divizibil cu 7, utilizez
				MediatorUser celulaMediator_sterge = new MediatorUser("celulaSterge"); // sablon Mediator
				celulaMediator_sterge.creazaCelulaPuzzle(0, "1");  // acest rand si cel de mai sus nu sunt folosite....doar pentru demonstrare utilizare sablon Mediator
				MediatorUser celulaMediator = new MediatorUser("celulaNormal");
				tablaPuzzle.getTabla().add(celulaMediator.creazaCelulaPuzzle(i, Integer.toString(i+1)));
			} else {           // pentru alte valori ale lui i (inclusiv impar), utilizez sablonul Bridge
				Bridge3_abstract_celulapuzzle celulaBridge = new Bridge4_implementare_celulapuzzle_abstract (
						i, Integer.toString(i+1), new Bridge2_implementare_celulapuzzle());
				tablaPuzzle.getTabla().add(celulaBridge.creaza_celula_puzzle());
				
			}
		}

		if(rb2.isSelected()) { // daca rb2 este selectat pe "Imagine"...spargem imaginea selectata
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
		btn1.setEnabled(false);
		btn2.setEnabled(false);
		btn4.setEnabled(true);
		frame.repaint();
	}
}
