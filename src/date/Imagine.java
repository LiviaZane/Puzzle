package date;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Vector;

import javax.imageio.ImageIO;

public class Imagine {
	
    public static Vector<BufferedImage> spargeImagine(String a, int dim) throws Exception {
	   	
    	BufferedImage img = ImageIO.read(new File(a));
    	BufferedImage imagine = resize(img, 650, 650);

        int latime = imagine.getWidth() / dim;                                           
        int inaltime = imagine.getHeight() / dim;
                                                            
        Vector<BufferedImage> imagini = new Vector<>(dim * dim);  //vector in care vom stoca subImaginile
        
        int x = 0, y = 0; // indecsi pentru lin (x) si col (y) aferenti matricei dupa care se decupeaza
		for (int i = 0; i < dim; i++) {
			y = 0;
            for (int j = 0; j < dim; j++) {
            	imagini.add(imagine.getSubimage(y, x, latime, inaltime));
                y += latime;
            }
            x += inaltime;
        }

        return imagini; 
    }
    
    private static BufferedImage resize(BufferedImage img, int height, int width) {
        Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }
    
}